package dev.ky3he4ik.chessproblems.presentation.view.chess

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.ActivityBoardBinding
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.Piece

class BoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardBinding
    private lateinit var tiles: Array<Array<BoardTileView>>

    private var selectedTile = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tiles = Array(8) { x -> Array(8) { y -> BoardTileView(this, x, y)} }
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
                if (rowNum == 0 && column == 0)
                    view.piece = Piece.BlackKing
                if (rowNum == 2 && column == 5)
                    view.piece = Piece.WhiteKnight
                view.setOnClickListener { onTileClickListener(it as BoardTileView, column, rowNum) }
                view.visibility = View.VISIBLE
                row.addView(view, column)
            }
            row.visibility = View.VISIBLE
            binding.boardTable.addView(row, 7 - rowNum)
        }
    }


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
                selectedTile = posX * 8 + posY
                tile.isSelectedTile = true
                // Todo: highlight possible moves
                // moves.forEach { m -> gridAdapter.getTile(m.posX, m.posY).isSelectedPosition = true }
            }
            else -> {
                // move
                val selectedX = selectedTile / 8
                val selectedY = selectedTile % 8
                selectedTile = -1
                tiles[selectedX][selectedY].isSelectedTile = false
                tiles.forEach { t ->
                    t.forEach {
                        it.isSelectedPosition = false
                    }
                }
                // Todo: move figure
                if (tiles[selectedX][selectedY].piece != null) {
                    tile.piece = tiles[selectedX][selectedY].piece
                    tiles[selectedX][selectedY].piece = null
                }
            }
        }
    }
}
