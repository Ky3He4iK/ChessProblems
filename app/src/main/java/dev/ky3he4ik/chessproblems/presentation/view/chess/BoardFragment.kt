package dev.ky3he4ik.chessproblems.presentation.view.chess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding
    private lateinit var boardGrid: List<List<BoardFragment>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(layoutInflater, container, false)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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