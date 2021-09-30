package dev.ky3he4ik.chessproblems.presentation.view.problems

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.AddProblemFragmentBinding
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove
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
        binding.image.setOnClickListener {
            setPhoto()
        }
        binding.image.setOnLongClickListener {
            viewModel.setImage(R.drawable.ic_baseline_add_circle_outline_24.toString())
            true
        }

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
                !movesRaw.isNullOrEmpty() && !whitePositions.isNullOrEmpty() && !blackPositions.isNullOrEmpty()
            ) {
                val moves = movesRaw.map { ProblemMove(it.first, it.second) }
                val positions = whitePositions.map { FigurePosition(true, it) } +
                        blackPositions.map { FigurePosition(false, it) }
                val problem =
                    ProblemInfo(
                        0,
                        title,
                        viewModel.image.value,
                        description,
                        difficulty,
                        whiteStarts,
                        moves,
                        positions
                    )
                viewModel.addProblem(problem)
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Insert all data", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun setPhoto() {
        requireActivity().activityResultRegistry.register(
            "key",
            ActivityResultContracts.OpenDocument()
        ) { result ->
            if (result != null) {
                requireActivity().applicationContext.contentResolver
                    .takePersistableUriPermission(result, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                viewModel.setImage(result.toString())
            }
        }.launch(arrayOf("image/*"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = navArgs<AddProblemFragmentArgs>().value.data
        if (data.isNotEmpty()) {
            val problem = ProblemOperations.fromUrl(data)
            if (problem != null) {
                binding.title.setText(problem.title)
                binding.description.setText(problem.description)
                binding.difficulty.setText(problem.difficulty.toString())
                binding.whiteStarts.isChecked = problem.whiteStarts
                problem.moves.forEach {
                    (binding.moves.adapter as AddProblemMovesListItemAdapter).addSection(it.posStart, it.posEnd)
                }
                problem.figurePosition.forEach {
                    val adapter = when (it.isWhite) {
                        true -> binding.whitePositions.adapter
                        false -> binding.blackPositions.adapter
                    }
                    (adapter as AddProblemPositionListItemAdapter).addSection(it.code)
                }
            }
        }
        viewModel = ViewModelProvider(this).get(AddProblemViewModel::class.java)
        viewModel.image.observe(viewLifecycleOwner, {
            if (it == null) {
                binding.image.setImageDrawable(null)
            } else {
                val uri = Uri.parse(it)
                binding.image.setImageBitmap(
                    BitmapFactory.decodeFileDescriptor(
                        requireContext().contentResolver.openFileDescriptor(
                            uri,
                            "r"
                        )?.fileDescriptor
                    )
                )
            }
        })
    }
}
