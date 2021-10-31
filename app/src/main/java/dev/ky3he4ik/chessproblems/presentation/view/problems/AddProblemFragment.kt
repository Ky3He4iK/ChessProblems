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
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.operations.ProblemOperations
import dev.ky3he4ik.chessproblems.presentation.view.problems.adapters.AddProblemMovesListItemAdapter
import dev.ky3he4ik.chessproblems.presentation.view.problems.adapters.AddProblemPositionListItemAdapter
import dev.ky3he4ik.chessproblems.presentation.viewmodel.problems.AddProblemViewModel

class AddProblemFragment : Fragment() {
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
            val moves = (binding.moves.adapter as AddProblemMovesListItemAdapter).data.value
            val whitePositions =
                (binding.whitePositions.adapter as AddProblemPositionListItemAdapter).data.value
            val blackPositions =
                (binding.blackPositions.adapter as AddProblemPositionListItemAdapter).data.value
            val positions = (whitePositions?.mapNotNull {
                ProblemOperations.parseFigure(it, true)
            } ?: listOf()) + (blackPositions?.mapNotNull {
                ProblemOperations.parseFigure(
                    it,
                    false
                )
            } ?: listOf())
            val movesParsed = if (moves.isNullOrEmpty()) listOf() else ProblemOperations.parseMoves(
                moves,
                whiteStarts,
                positions
            )
            if (title.isNotEmpty() && difficulty != null && !whitePositions.isNullOrEmpty()
                && !blackPositions.isNullOrEmpty() && !movesParsed.isNullOrEmpty()
            ) {
                val problem =
                    ProblemInfo(
                        0,
                        title,
                        description,
                        difficulty,
                        whiteStarts,
                        movesParsed,
                        positions,
                    )
                viewModel.addProblem(problem)
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Insert all data", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val data = navArgs<AddProblemFragmentArgs>().value.data
//        if (data.isNullOrEmpty())
//         todo: fix loading
        setFromProblemInfo(null)
//        else
//            setFromProblemInfo(ProblemOperations.fromUrl(data))

        viewModel = ViewModelProvider(this).get(AddProblemViewModel::class.java)
    }

    private fun setFromProblemInfo(problem: ProblemInfo?) {
        binding.title.setText(problem?.title ?: "")
        binding.description.setText(problem?.description ?: "")
        binding.difficulty.setText(problem?.difficulty?.toString() ?: "")
        binding.whiteStarts.isChecked = problem?.whiteStarts ?: true
        binding.moves.removeAllViewsInLayout()
        (binding.moves.adapter as AddProblemMovesListItemAdapter).clear()
        problem?.moves?.forEach {
            (binding.moves.adapter as AddProblemMovesListItemAdapter).addSection(it.move)
        }
        (binding.whitePositions.adapter as AddProblemPositionListItemAdapter).clear()
        binding.whitePositions.removeAllViewsInLayout()
        (binding.blackPositions.adapter as AddProblemPositionListItemAdapter).clear()
        binding.blackPositions.removeAllViewsInLayout()
        problem?.figurePosition?.forEach {
            val adapter = when (it.isWhite) {
                true -> binding.whitePositions.adapter
                false -> binding.blackPositions.adapter
            }
            (adapter as AddProblemPositionListItemAdapter).addSection(it.toInfoString())
        }
    }
}
