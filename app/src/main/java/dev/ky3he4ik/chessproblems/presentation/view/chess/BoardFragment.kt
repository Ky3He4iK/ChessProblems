package dev.ky3he4ik.chessproblems.presentation.view.chess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableRow
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBindings
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.BoardFragmentBinding
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.BitmapStorage
import dev.ky3he4ik.chessproblems.presentation.view.chess.utils.Piece

class BoardFragment : Fragment() {
//    private val gridItems = arrayOf(
//        arrayOf(R.id.a1, R.id.a2, R.id.a3, R.id.a4, R.id.a5, R.id.a6, R.id.a7, R.id.a8),
//        arrayOf(R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8),
//        arrayOf(R.id.c1, R.id.c2, R.id.c3, R.id.c4, R.id.c5, R.id.c6, R.id.c7, R.id.c8),
//        arrayOf(R.id.d1, R.id.d2, R.id.d3, R.id.d4, R.id.d5, R.id.d6, R.id.d7, R.id.d8),
//        arrayOf(R.id.e1, R.id.e2, R.id.e3, R.id.e4, R.id.e5, R.id.e6, R.id.e7, R.id.e8),
//        arrayOf(R.id.f1, R.id.f2, R.id.f3, R.id.f4, R.id.f5, R.id.f6, R.id.f7, R.id.f8),
//        arrayOf(R.id.g1, R.id.g2, R.id.g3, R.id.g4, R.id.g5, R.id.g6, R.id.g7, R.id.g8),
//        arrayOf(R.id.h1, R.id.h2, R.id.h3, R.id.h4, R.id.h5, R.id.h6, R.id.h7, R.id.h8),
//    )
    private lateinit var binding: BoardFragmentBinding
//    private lateinit var tiles: Array<Array<BoardTileView>>
    private lateinit var tiles: Array<Array<ImageView>>

    private var selectedTile = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BoardFragmentBinding.inflate(layoutInflater, container, false)

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
//        tiles = Array(8) { x -> Array(8) { y -> BoardTileView(context, x, y)} }

        val intent = Intent(context, BoardActivity::class.java)
        startActivity(intent)


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
