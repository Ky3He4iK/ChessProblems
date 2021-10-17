package dev.ky3he4ik.chessproblems.presentation.view.users

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ky3he4ik.chessproblems.R
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
        binding.image.setOnClickListener {
            setPhoto()
        }
        binding.image.setOnLongClickListener {
            viewModel.setImage(null)
            true
        }

        binding.saveButton.setOnClickListener {
            val nick = binding.nickname.text.toString()
            val rating = binding.rating.text.toString().toIntOrNull()
            val solvedProblems =
                (binding.recyclerView.adapter as AddUserSolvedProblemListItemAdapter).data

            if (nick.isNotEmpty() && rating != null) {
                val solvedProblemsModel =
                    solvedProblems.value?.map { SolvedProblem(it.first, it.second) }
                        ?: return@setOnClickListener
                val user =
                    UserInfo(
                        0,
                        nick,
                        viewModel.image.value,
                        rating,
                        solvedProblemsModel.size,
                        solvedProblemsModel
                    )
                viewModel.addUser(user)
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Insert all data", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun setPhoto() {
        try {
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
        } catch (e: Exception) {
            Log.e("Chess/AUF", e.toString(), e)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddUserViewModel::class.java)
        viewModel.image.observe(viewLifecycleOwner, {
            if (it == null) {
                binding.image.setImageResource(R.drawable.ic_baseline_add_circle_outline_24)
                return@observe
            }
            try {
                binding.image.setImageBitmap(
                    BitmapFactory.decodeFileDescriptor(
                        requireContext().contentResolver.openFileDescriptor(
                            Uri.parse(it),
                            "r"
                        )?.fileDescriptor
                    )
                )
            } catch (e: Exception) {
                Log.e("Chess/AUF", e.toString(), e)
            }
        })
    }
}
