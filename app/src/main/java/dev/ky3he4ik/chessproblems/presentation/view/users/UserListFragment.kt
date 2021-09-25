package dev.ky3he4ik.chessproblems.presentation.view.users

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
import dev.ky3he4ik.chessproblems.databinding.UserListFragmentBinding
import dev.ky3he4ik.chessproblems.presentation.view.users.adapters.UserListElementAdapter
import dev.ky3he4ik.chessproblems.presentation.viewmodel.users.UserListViewModel

class UserListFragment : Fragment() {
    private lateinit var viewModel: UserListViewModel
    private lateinit var binding: UserListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserListFragmentBinding.inflate(layoutInflater, container, false)
        binding.userRecyclerView.also {
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
                viewModel.deleteUser(
                    (binding.userRecyclerView.adapter as UserListElementAdapter).data[position]
                )
            }
        }).attachToRecyclerView(binding.userRecyclerView)
        binding.floatingActionButton2.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListToAddUser()
            it.findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)

        viewModel.getUsersList().observe(viewLifecycleOwner, {
            binding.userRecyclerView.adapter = UserListElementAdapter(it)
        })
    }
}
