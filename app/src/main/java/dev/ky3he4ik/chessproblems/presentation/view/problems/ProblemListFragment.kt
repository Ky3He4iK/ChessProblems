package dev.ky3he4ik.chessproblems.presentation.view.problems

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.databinding.ProblemListFragmentBinding
import dev.ky3he4ik.chessproblems.presentation.view.chess.BoardActivity
import dev.ky3he4ik.chessproblems.presentation.view.problems.adapters.ProblemListElementAdapter
import dev.ky3he4ik.chessproblems.presentation.viewmodel.problems.ProblemListViewModel

class ProblemListFragment : Fragment() {
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
            val action = ProblemListFragmentDirections.actionProblemListToAddProblem()
            it.findNavController().navigate(action)
        }
        RecyclerItemClickListener.registerListener(context, binding.problemRecyclerView,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val data = (binding.problemRecyclerView.adapter as ProblemListElementAdapter).data
                    if (position in data.indices) {
                        val intent = Intent(context, BoardActivity::class.java)
                        intent.putExtra(BoardActivity.PROBLEM_ID, data[position].problemId)
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, "Invalid position: $position", Toast.LENGTH_SHORT).show()
                        Log.e("ProblemListFragment/click", "Invalid position: $position")
                    }
                }

                override fun onLongItemClick(view: View?, position: Int) {}
            })
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
