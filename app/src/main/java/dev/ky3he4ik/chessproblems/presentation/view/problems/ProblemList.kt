package dev.ky3he4ik.chessproblems.presentation.view.problems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.databinding.ProblemListFragmentBinding
import dev.ky3he4ik.chessproblems.presentation.view.problems.adapters.ProblemListElementAdapter
import dev.ky3he4ik.chessproblems.presentation.viewmodel.problems.ProblemListViewModel

class ProblemList : Fragment() {
    private lateinit var viewModel: ProblemListViewModel
    private lateinit var binding: ProblemListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProblemListFragmentBinding.inflate(layoutInflater, container, false)
        binding.problemRecyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(false)
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteProblem(
                    (binding.problemRecyclerView.adapter as ProblemListElementAdapter).data[position]
                )
            }
        }).attachToRecyclerView(binding.problemRecyclerView)
        binding.floatingActionButton.setOnClickListener {
            val action = ProblemListDirections.actionProblemListToAddProblem()
            it.findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProblemListViewModel::class.java)

        viewModel.getProblemsList().observe(viewLifecycleOwner, {
            binding.problemRecyclerView.adapter = ProblemListElementAdapter(it)
        })
    }
}
