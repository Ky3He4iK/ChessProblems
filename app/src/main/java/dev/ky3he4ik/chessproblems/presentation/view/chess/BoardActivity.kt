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
//    private lateinit var tiles: Array<Array<ImageView>>

    private var selectedTile = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_board)
        setContentView(binding.root)
//        binding.text.text = "asdasd2"
//        tiles = arrayOf(
//            arrayOf(binding.a1, binding.a2, binding.a3, binding.a4, binding.a5, binding.a6, binding.a7, binding.a8),
//            arrayOf(binding.b1, binding.b2, binding.b3, binding.b4, binding.b5, binding.b6, binding.b7, binding.b8),
//            arrayOf(binding.c1, binding.c2, binding.c3, binding.c4, binding.c5, binding.c6, binding.c7, binding.c8),
//            arrayOf(binding.d1, binding.d2, binding.d3, binding.d4, binding.d5, binding.d6, binding.d7, binding.d8),
//            arrayOf(binding.e1, binding.e2, binding.e3, binding.e4, binding.e5, binding.e6, binding.e7, binding.e8),
//            arrayOf(binding.f1, binding.f2, binding.f3, binding.f4, binding.f5, binding.f6, binding.f7, binding.f8),
//            arrayOf(binding.g1, binding.g2, binding.g3, binding.g4, binding.g5, binding.g6, binding.g7, binding.g8),
//            arrayOf(binding.h1, binding.h2, binding.h3, binding.h4, binding.h5, binding.h6, binding.h7, binding.h8),
//            )
        tiles = Array(8) { x -> Array(8) { y -> BoardTileView(this, x, y)} }
//        tiles = Array(8) { x -> Array(8) { y -> ImageView(this) } }
        for (rowNum in 0 until 8) {
            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            row.id = 12345678 + rowNum

            for (column in 0 until 8) {
                val view = tiles[column][rowNum]
                view.id = 123456789 + rowNum + column * 8
                view.posX = column
                view.posY = rowNum
                if (rowNum == 0 && column == 0)
                    view.piece = Piece.BlackKing
                if (rowNum == 2 && column == 5)
                    view.piece = Piece.WhiteKnight
//                if ((rowNum + column) % 2 == 0)
//                    view.setImageBitmap(BitmapStorage.getBitmap(R.drawable.background_green, requireContext()))
//                view.setImageResource(R.drawable.piece_white_queen)
//                else
//                    view.setImageBitmap(BitmapStorage.getBitmap(R.drawable.background_white, requireContext()))
                view.setOnClickListener { onTileClickListener(it as BoardTileView, column, rowNum) }
                view.visibility = View.VISIBLE
                row.addView(view, column)
            }
            row.visibility = View.VISIBLE
//            findViewById<TableLayout>(R.id.boardTable).addView(row, rowNum)
            binding.boardTable.addView(row, rowNum)
        }

//        binding.text.setText("asdasd")
//        findViewById<TextView>(R.id.text).text = "awddfvf"
    }


    private fun onTileClickListener(tile: BoardTileView, posX: Int, posY: Int) {
        Log.d("BoardFragment", "$posX $posY")
        Toast.makeText(this, "Click $posX $posY", Toast.LENGTH_SHORT).show()
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
                val piece = tile.piece
                tile.piece = tiles[selectedX][selectedY].piece
                tiles[selectedX][selectedY].piece = piece
            }
        }
    }
}