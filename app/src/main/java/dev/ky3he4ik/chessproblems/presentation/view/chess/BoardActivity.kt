package dev.ky3he4ik.chessproblems.presentation.view.chess

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dev.ky3he4ik.chessproblems.databinding.ActivityBoardBinding
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.Piece
import dev.ky3he4ik.chessproblems.presentation.viewmodel.chess.BoardViewModel

class BoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardBinding
    private lateinit var tiles: Array<Array<BoardTileView>>
    private lateinit var viewModel: BoardViewModel

    private var selectedTile = -1
    private var problem: ProblemInfo? = null
    private var currentMove = 0
    private var moves: ArrayList<ProblemMove> = arrayListOf()
    private var isFlipped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(BoardViewModel::class.java)

        tiles = Array(8) { x -> Array(8) { y -> BoardTileView(this, x, y) } }
        for (rowNum in 7 downTo 0) {
            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            for (column in 0 until 8) {
                val view = tiles[column][rowNum]
                view.posX = column
                view.posY = rowNum
                view.setOnClickListener { onTileClickListener(it as BoardTileView, column, rowNum) }
                view.visibility = View.VISIBLE
                row.addView(view, column)
            }
            row.visibility = View.VISIBLE
            binding.boardTable.addView(row, 7 - rowNum)
        }

        viewModel.getProblemInfo(intent.getIntExtra(PROBLEM_ID, 0)).observe(this) {
            if (it == null) {
                Toast.makeText(applicationContext, "Invalid problem", Toast.LENGTH_SHORT).show()
                finish()
                return@observe
            }
            problem = it
            isFlipped = !(problem?.whiteStarts ?: true) // false if no problem
            for (x in 0 until 8)
                for (y in 0 until 8) {
                    val tile = getTile(x, y)
                    tile.isBoardFlipped = isFlipped
                    tile.posX = x
                    tile.posY = y
                }
            problem?.figurePosition?.forEach { figure ->

                if ((figure.letter - 'a') in 0..7 && figure.number in 0..7) {
                    val piece = Piece.values()
                        .find { piece -> piece.letter == figure.figure && piece.isWhite == figure.isWhite }
                    getTile(figure.letter - 'a', figure.number).piece = piece
                }
            }
            moves = ArrayList(it.moves)
        }
    }

    private fun getTile(x: Int, y: Int): BoardTileView =
        tiles[if (isFlipped) 7 - x else x][if (isFlipped) 7 - y else y]

    private fun getTile(tileNum: Int): BoardTileView = getTile(tileNum / 8, tileNum % 8)

    private fun onTileClickListener(tile: BoardTileView, posX: Int, posY: Int) {
        Log.d("BoardFragment", "$posX $posY")
        when {
            tile.isSelectedTile -> {
                // remove selection
                selectedTile = -1
                tile.isSelectedTile = false
                tiles.forEach { t ->
                    t.forEach {
                        it.isSelectedPosition = false
                    }
                }
            }
            selectedTile == -1 -> {
                // select
                selectedTile = tile.posX * 8 + tile.posY
                tile.isSelectedTile = true
                // Todo: highlight possible moves
                // moves.forEach { m -> gridAdapter.getTile(m.posX, m.posY).isSelectedPosition = true }
            }
            getTile(selectedTile).piece == null -> {
                // no move
                getTile(selectedTile).isSelectedTile = false
                tiles.forEach { t ->
                    t.forEach {
                        it.isSelectedPosition = false
                    }
                }
            }
            else -> {
                // move
                val selectedX = selectedTile / 8
                val selectedY = selectedTile % 8
                selectedTile = -1
                val selected = getTile(selectedX, selectedY)
                selected.isSelectedTile = false
                tiles.forEach { t ->
                    t.forEach {
                        it.isSelectedPosition = false
                    }
                }
                if (movePiece(selectedX, selectedY, tile.posX, tile.posY, moves[currentMove])) {
                    currentMove++
                    if (currentMove < moves.size) {
                        val move = moves[currentMove]
                        movePiece(move.letterStart, move.numberStart, move.letterEnd, move.numberEnd, move)
                        currentMove++
                    }
                    if (currentMove >= moves.size) {
                        Toast.makeText(applicationContext, "Solved!", Toast.LENGTH_SHORT).show()
                        if (problem != null)
                            viewModel.registerProblemSolved(problem?.problemId ?: 0, 0, 0)
                        finish()
                    }
                } else {
                    Toast.makeText(applicationContext, "Wrong move", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun movePiece(srcX: Int, srcY: Int, destX: Int, destY: Int, move: ProblemMove): Boolean {
        val source = getTile(srcX, srcY)
        val destination = getTile(destX, destY)
        // Todo: check if move allowed
        if (srcX == move.letterStart && srcY == move.numberStart
            && destX == move.letterEnd && destY == move.numberEnd
        ) {
            if (move.isCastling) {
                when {
                    problem?.whiteStarts == true && move.move == "O-O" && destX == 6 && destY == 0 && srcX == 4 && srcY == 0 -> {
                        val piece = source.piece
                        source.piece = null
                        getTile(5, 0).piece = getTile(7, 0).piece
                        getTile(7, 0).piece = null
                        destination.piece = piece
                    }
                    problem?.whiteStarts == true && move.move == "O-O-O" && destX == 2 && destY == 0 && srcX == 4 && srcY == 0 -> {
                        val piece = source.piece
                        source.piece = null
                        getTile(3, 0).piece = getTile(0, 0).piece
                        getTile(0, 0).piece = null
                        destination.piece = piece
                    }
                    problem?.whiteStarts == false && move.move == "O-O" && destX == 6 && destY == 7 && srcX == 4 && srcY == 7 -> {
                        val piece = source.piece
                        source.piece = null
                        getTile(5, 7).piece = getTile(7, 7).piece
                        getTile(7, 7).piece = null
                        destination.piece = piece
                    }
                    problem?.whiteStarts == false && move.move == "O-O-O" && destX == 2 && destY == 7 && srcX == 4 && srcY == 7 -> {
                        val piece = source.piece
                        source.piece = null
                        getTile(3, 7).piece = getTile(0, 7).piece
                        getTile(0, 7).piece = null
                        destination.piece = piece
                    }
                    else -> {
                        return false
                    }
                }
                return true
            } else {
                destination.piece = source.piece
                source.piece = null
                if (move.promotion != null)
                    destination.piece =
                        Piece.values().find { it.letter == move.promotion?.uppercaseChar() }
                //todo: animation
                return true
            }
        }
        return false
    }

    companion object {
        const val PROBLEM_ID = "ProblemId"
    }
}
