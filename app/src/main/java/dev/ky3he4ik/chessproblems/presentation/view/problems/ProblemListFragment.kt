package dev.ky3he4ik.chessproblems.presentation.view.problems

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.presentation.view.chess.BoardActivity
import dev.ky3he4ik.chessproblems.presentation.view.problems.adapters.ProblemListElementAdapter
import dev.ky3he4ik.chessproblems.presentation.viewmodel.problems.ProblemListViewModel

class ProblemListFragment : Fragment() {
    private lateinit var viewModel: ProblemListViewModel
    private lateinit var binding: ProblemListFragmentBinding

    private var gettingProblem = false
    private var canEdit: Boolean = false

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
                if (canEdit) {
                    viewModel.deleteProblem(
                        (binding.problemRecyclerView.adapter as ProblemListElementAdapter).data[position]
                    )
                }
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                if (!canEdit) return 0
                return super.getSwipeDirs(recyclerView, viewHolder)
            }
        }).attachToRecyclerView(binding.problemRecyclerView)
        binding.floatingActionButton.setOnClickListener {
            if (canEdit) {
                val action = ProblemListFragmentDirections.actionProblemListToAddProblem()
                it.findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Access denied", Toast.LENGTH_SHORT).show()
            }
        }
        RecyclerItemClickListener.registerListener(context, binding.problemRecyclerView,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    if (viewModel.getActiveUserId() == null) {
                        Toast.makeText(context, "No user selected", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val data =
                        (binding.problemRecyclerView.adapter as ProblemListElementAdapter).data
                    if (position in data.indices) {
                        openBoard(data[position].problemId)
                    } else {
                        Toast.makeText(context, "Invalid position: $position", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("ProblemListFragment/click", "Invalid position: $position")
                    }
                }

                override fun onLongItemClick(view: View?, position: Int) {}
            })

        binding.getButton.setOnClickListener {
            if (viewModel.getActiveUserId() == null)
                Toast.makeText(context, "Warning! User is not selected", Toast.LENGTH_SHORT).show()
            if (gettingProblem) {
                Toast.makeText(context, "Already loading", Toast.LENGTH_SHORT).show()
            } else {
                gettingProblem = true
                Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                viewModel.getRandomProblem().observe(viewLifecycleOwner) { value: ProblemInfo? ->
                    gettingProblem = false
                    if (value == null)
                        Toast.makeText(context, "Can't get data", Toast.LENGTH_SHORT).show()
                    else {
                        viewModel.addProblem(value).observe(viewLifecycleOwner) {
                            openBoard(it)
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProblemListViewModel::class.java)

        viewModel.getUnsolvedProblemsListLive().observe(viewLifecycleOwner) {
            binding.problemRecyclerView.adapter =
                ProblemListElementAdapter(it, context ?: return@observe)
        }

        viewModel.getActiveUserId()?.let {
            viewModel.getUser(it).observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    canEdit = viewModel.canAddProblems(user)
                    binding.floatingActionButton.visibility =
                        if (canEdit) View.VISIBLE else View.GONE
                } else {
                    canEdit = false
                    binding.floatingActionButton.visibility = View.GONE
                }
            }
        }
    }

    private fun openBoard(problemId: Int) {
        val intent = Intent(context, BoardActivity::class.java)
        intent.putExtra(BoardActivity.PROBLEM_ID, problemId)
        startActivity(intent)
    }
}
