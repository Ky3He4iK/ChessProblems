package dev.ky3he4ik.chessproblems.presentation.view.chess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.BoardFragmentBinding
import dev.ky3he4ik.chessproblems.presentation.view.chess.adapters.BoardTileGridAdapter
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.Piece

class BoardFragment : Fragment() {
    private lateinit var binding: BoardFragmentBinding
    private lateinit var gridAdapter: BoardTileGridAdapter
    private var selectedTile = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BoardFragmentBinding.inflate(layoutInflater, container, false)
        gridAdapter = BoardTileGridAdapter(context)

        binding.tileGrid.apply {
            adapter = gridAdapter

            setOnItemClickListener { parent, view, position, id ->
                val tile = view as BoardTileView
                when {
                    tile.isSelectedTile -> {
                        // remove selection
                        selectedTile = -1
                        tile.isSelectedTile = false
                        gridAdapter.tiles.forEach { t ->
                            t?.isSelectedPosition = false
                        }
                    }
                    selectedTile == -1 -> {
                        // select
                        selectedTile = position
                        tile.isSelectedTile = true
                        // Todo: highlight possible moves
                        // moves.forEach { m -> gridAdapter.getTile(m.posX, m.posY).isSelectedPosition = true }
                    }
                    else -> {
                        // move
                        val selectedX = selectedTile / 8
                        val selectedY = selectedTile % 8
                        selectedTile = -1
                        tile.isSelectedTile = false
                        gridAdapter.tiles.forEach { t ->
                            t?.isSelectedPosition = false
                        }
                        // Todo: move figure
                        tile.piece = gridAdapter.tiles[selectedTile]?.piece
                        gridAdapter.tiles[selectedTile]?.piece = null
                    }
                }
            }
        }

        gridAdapter.tiles[0]?.piece = Piece.BlackKing

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.board_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Todo: viewModel with observers
//        viewModel = ViewModelProvider(this).get(AddProblemViewModel::class.java)
//        viewModel.image.observe(viewLifecycleOwner, {
//            if (it == null) {
//                binding.image.setImageResource(R.drawable.ic_baseline_add_circle_outline_24)
//                return@observe
//            }
//            try {
//                binding.image.setImageBitmap(
//                    BitmapFactory.decodeFileDescriptor(
//                        requireContext().contentResolver.openFileDescriptor(
//                            Uri.parse(it),
//                            "r"
//                        )?.fileDescriptor
//                    )
//                )
//            } catch (e: Exception) {
//                Log.e("Chess/AUF", e.toString(), e)
//            }
//        })
    }

}