package dev.ky3he4ik.chessproblems.presentation.view.problems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ky3he4ik.chessproblems.databinding.AddProblemFragmentBinding
import dev.ky3he4ik.chessproblems.presentation.view.problems.adapters.AddProblemMovesListItemAdapter
import dev.ky3he4ik.chessproblems.presentation.view.problems.adapters.AddProblemPositionListItemAdapter
import dev.ky3he4ik.chessproblems.presentation.viewmodel.problems.AddProblemViewModel

class AddProblem : Fragment() {
    private lateinit var viewModel: AddProblemViewModel
    private lateinit var binding: AddProblemFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddProblemFragmentBinding.inflate(layoutInflater, container, false)
        binding.moves.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(false)
            it.adapter = AddProblemMovesListItemAdapter()
        }
        binding.addMove.setOnClickListener {
            (binding.moves.adapter as AddProblemMovesListItemAdapter).addSection()
        }
        binding.whitePositions.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(false)
            it.adapter = AddProblemPositionListItemAdapter()
        }
        binding.addWhite.setOnClickListener {
            (binding.whitePositions.adapter as AddProblemPositionListItemAdapter).addSection()
        }
        binding.blackPositions.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(false)
            it.adapter = AddProblemPositionListItemAdapter()
        }
        binding.addBlack.setOnClickListener {
            (binding.blackPositions.adapter as AddProblemPositionListItemAdapter).addSection()
        }
        binding.whiteStarts.isChecked = true

        binding.saveButton.setOnClickListener {
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            val difficulty = binding.difficulty.text.toString().toIntOrNull()
            val whiteStarts = binding.whiteStarts.isChecked
            val movesRaw = (binding.moves.adapter as AddProblemMovesListItemAdapter).data.value
            val whitePositions =
                (binding.whitePositions.adapter as AddProblemPositionListItemAdapter).data.value
            val blackPositions =
                (binding.blackPositions.adapter as AddProblemPositionListItemAdapter).data.value
            if (title.isNotEmpty() && description.isNotEmpty() && difficulty != null &&
                movesRaw != null && whitePositions != null && blackPositions != null &&
                movesRaw.isNotEmpty() && whitePositions.isNotEmpty() && blackPositions.isNotEmpty()
            ) {
                viewModel.addProblem(
                    title,
                    description,
                    difficulty,
                    whiteStarts,
                    movesRaw,
                    whitePositions,
                    blackPositions
                )
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Insert all data", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddProblemViewModel::class.java)
    }
}
