package dev.ky3he4ik.chessproblems.presentation.view.chess.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.BoardGridElementBinding
import dev.ky3he4ik.chessproblems.presentation.view.chess.BoardTileView


class BoardTileGridAdapter(val context: Context?) : BaseAdapter() {
//    val tiles = Array(64) { BoardTileView(it / 8, it % 8, context) }
    val tiles = Array<BoardTileView?>(64) { null }

    override fun getCount(): Int = 64

    override fun getItem(position: Int): Any = tiles[position]!!

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (parent == null)
            return View(context)

        val binding: BoardGridElementBinding =
            BoardGridElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val grid = convertView
            ?: (context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?)?.inflate(
                R.layout.board_grid_element,
                parent,
                false
            ) ?: View(context)
        binding.tileView.posX = position / 8
        binding.tileView.posY = position % 8

        tiles[position] = binding.tileView

        return grid
    }

    fun getTile(posX: Int, posY: Int): BoardTileView = tiles[posX * 8 + posY]!!
}
