package dev.ky3he4ik.chessproblems.presentation.view.chess

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
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
import dev.ky3he4ik.chessproblems.presentation.repository.Repository
import dev.ky3he4ik.chessproblems.presentation.service.StopwatchService
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.Piece
import dev.ky3he4ik.chessproblems.presentation.viewmodel.chess.BoardViewModel
import kotlin.math.max

class BoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardBinding
    private lateinit var tiles: Array<Array<BoardTileView>>
    private lateinit var viewModel: BoardViewModel
    private lateinit var serviceIntent: Intent

    private var selectedTile = -1
    private var problem: ProblemInfo? = null
    private var currentMove = 0
    private var openedMoves = 0
    private var moves: ArrayList<ProblemMove> = arrayListOf()
    private var isFlipped = false
    private var timeStart: Long = 0
    private var hintedCells = arrayListOf<Int>()

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val time = intent.getIntExtra(StopwatchService.TIME_EXTRA, 0) / 1000
            binding.boardTime.text = String.format("%02d:%02d", time / 60, time % 60)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timeStart = System.currentTimeMillis()
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(BoardViewModel::class.java)

        buildBoard()

        viewModel.getProblemInfo(intent.getIntExtra(PROBLEM_ID, 0)).observe(this) {
            if (it == null || it.moves.isEmpty()) {
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
            Log.d("BoardActivity/Moves", moves.joinToString("; "))
        }

        binding.boardExit.setOnClickListener {
            finish()
        }

        //todo: move
//        binding.boardNextMove.setOnClickListener {
//            currentMove++
//        }
//        binding.boardPreviousMove.setOnClickListener {
//            currentMove--
//        }


        binding.boardHelp.setOnClickListener {
            if (currentMove >= moves.size) {
                Toast.makeText(this, "You already solved!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val startPos = moves[currentMove].letterStart * 8 + moves[currentMove].numberStart
            val endPos = moves[currentMove].letterEnd * 8 + moves[currentMove].numberEnd
            hintedCells.forEach {
                getTile(it).isSelectedPosition = false
            }
            hintedCells.add(startPos)
            getTile(startPos).isSelectedPosition = true
            hintedCells.add(endPos)
            getTile(endPos).isSelectedPosition = true
        }

        serviceIntent = Intent(applicationContext, StopwatchService::class.java)
        registerReceiver(updateTime, IntentFilter(StopwatchService.TIMER_UPDATED))
        showMoves()
    }

    private fun buildBoard() {
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
    }

    private fun getTile(x: Int, y: Int): BoardTileView =
        tiles[if (isFlipped) 7 - x else x][if (isFlipped) 7 - y else y]

    private fun getTile(tileNum: Int): BoardTileView = getTile(tileNum / 8, tileNum % 8)

    private fun onTileClickListener(tile: BoardTileView, posX: Int, posY: Int) {
        hintedCells.forEach {
            getTile(it).isSelectedPosition = false
        }
        when {
            tile.isSelectedTile -> {
                // remove selection
                tile.isSelectedTile = false
                selectedTile = -1
            }
            selectedTile == -1 -> {
                // select
                selectedTile = tile.posX * 8 + tile.posY
                tile.isSelectedTile = true
                // Todo: highlight possible moves
                // moves.forEach { m -> gridAdapter.getTile(m.posX, m.posY).isSelectedPosition = true }
            }
            getTile(selectedTile).piece == null -> {
                // select this
                getTile(selectedTile).isSelectedTile = false
                selectedTile = tile.posX * 8 + tile.posY
                tile.isSelectedTile = true
            }
            else -> {
                // move
                val selected = getTile(selectedTile)
                selected.isSelectedTile = false
                selectedTile = -1
                if (movePiece(selected.posX, selected.posY, tile.posX, tile.posY, moves[currentMove])) {
                    currentMove++
                    openedMoves = max(openedMoves, currentMove)
                    if (currentMove < moves.size) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            //Do something after 1s
                            val move = moves[currentMove]
                            movePiece(
                                move.letterStart,
                                move.numberStart,
                                move.letterEnd,
                                move.numberEnd,
                                move
                            )
                            currentMove++
                            openedMoves = max(openedMoves, currentMove)
                            if (currentMove >= moves.size) {
                                Toast.makeText(applicationContext, "Solved!", Toast.LENGTH_SHORT)
                                    .show()
                                if (problem != null)
                                    viewModel.registerProblemSolved(problem?.problemId ?: 0, 0, 0)
                            }
                            showMoves()
                        }, 1000)
                    } else {
                        Toast.makeText(applicationContext, "Solved!", Toast.LENGTH_SHORT).show()
                        if (problem != null)
                            viewModel.registerProblemSolved(
                                problem?.problemId ?: 0,
                                Repository.activeUserId ?: 0,
                                System.currentTimeMillis() - timeStart
                            )
                    }
                    showMoves()
                } else {
                    Toast.makeText(applicationContext, "Wrong move", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun movePiece(
        srcX: Int,
        srcY: Int,
        destX: Int,
        destY: Int,
        move: ProblemMove
    ): Boolean {
        val source = getTile(srcX, srcY)
        val destination = getTile(destX, destY)
        // Todo: check if move allowed
        if (srcX == move.letterStart && srcY == move.numberStart
            && destX == move.letterEnd && destY == move.numberEnd
        ) {
            problem?.let { problem ->
                if (move.isCastling) {
                    var exchangePos: Pair<Int, Int>? = null
                    if (problem.whiteStarts && destY == 0 && srcY == 0 && srcX == 4) {
                        if (move.move == "O-O" && destX == 6)
                            exchangePos = 5 * 8 to 7 * 8
                        else if (move.move == "O-O-O" && destX == 2)
                            exchangePos = 3 * 8 to 0 * 8
                    } else if (!problem.whiteStarts && destY == 7 && srcY == 7 && srcX == 4) {
                        if (move.move == "O-O" && destX == 6)
                            exchangePos = 5 * 8 + 7 to 7 * 8 + 7
                        else if (move.move == "O-O-O" && destX == 2)
                            exchangePos = 3 * 8 + 7 to 0 * 8 + 7
                    }
                    exchangePos ?: return false

                    val piece = source.piece
                    source.piece = null
                    getTile(exchangePos.second).apply {
                        getTile(exchangePos.first).piece = this.piece
                        this.piece = null
                    }
                    destination.piece = piece
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
        }
        return false
    }

    private fun showMoves() {
        problem?.let { problem ->
            val bias = if (problem.whiteStarts) 0 else 1
            val sb = StringBuilder()
            var i = bias
            if (!problem.whiteStarts && openedMoves > 0) {
                var s = "1. ... - ${moves[0].move}"
                if (currentMove == 0)
                    s = "<b>$s</b>"
                sb.append(s)
            }
            while (i < openedMoves) {
                var s: String = if (i + 1 < openedMoves)
                    "${i / 2 + 1}. ${moves[i].move} - ${moves[i + 1].move}"
                else
                    "${i / 2 + 1}. ${moves[i].move}"
                if (i == currentMove || i + 1 == currentMove)
                    s = "<b>$s</b>"
                sb.append(s)
                i += 2
            }
            if (Build.VERSION.SDK_INT >= 24)
                binding.boardMoveList.text =
                    Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_COMPACT);
            else
                binding.boardMoveList.text = Html.fromHtml(sb.toString());
        }
    }


    companion object {
        const val PROBLEM_ID = "ProblemId"
    }
}
