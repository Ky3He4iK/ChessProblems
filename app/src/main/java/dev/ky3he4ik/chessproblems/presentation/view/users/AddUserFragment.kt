package dev.ky3he4ik.chessproblems.presentation.view.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ky3he4ik.chessproblems.databinding.AddUserFragmentBinding
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.view.users.adapters.AddUserSolvedProblemListItemAdapter
import dev.ky3he4ik.chessproblems.presentation.viewmodel.users.AddUserViewModel

class AddUserFragment : Fragment() {
    private lateinit var viewModel: AddUserViewModel
    private lateinit var binding: AddUserFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddUserFragmentBinding.inflate(layoutInflater, container, false)
        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(false)
            it.adapter = AddUserSolvedProblemListItemAdapter()
        }
        binding.button.setOnClickListener {
            (binding.recyclerView.adapter as AddUserSolvedProblemListItemAdapter).addSection()
        }

        binding.saveButton.setOnClickListener {
            val nick = binding.nickname.text.toString()
            val rating = binding.rating.text.toString().toIntOrNull()
            val solvedProblems = (binding.recyclerView.adapter as AddUserSolvedProblemListItemAdapter).data
            if (nick.isNotEmpty() && rating != null) {
                val solvedProblemsModel = solvedProblems.value!!.map { SolvedProblem(it.first, it.second) }
                val user = UserInfo(0, nick, rating, solvedProblemsModel.size, solvedProblemsModel)
                viewModel.addUser(user)
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Insert all data", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddUserViewModel::class.java)
    }
}
