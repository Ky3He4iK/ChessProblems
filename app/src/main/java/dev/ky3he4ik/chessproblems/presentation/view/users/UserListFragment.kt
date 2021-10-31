package dev.ky3he4ik.chessproblems.presentation.view.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.MainActivity
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.UserListFragmentBinding
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserInfoDTO
import dev.ky3he4ik.chessproblems.presentation.view.problems.RecyclerItemClickListener
import dev.ky3he4ik.chessproblems.presentation.view.users.adapters.UserListElementAdapter
import dev.ky3he4ik.chessproblems.presentation.viewmodel.users.UserListViewModel

class UserListFragment : Fragment() {
    private lateinit var viewModel: UserListViewModel
    private lateinit var binding: UserListFragmentBinding
    private lateinit var touchHelper: ItemTouchHelper

    private var isFabOpen = false
    private var canDelete = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserListFragmentBinding.inflate(layoutInflater, container, false)
        binding.userRecyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(false)
        }
        touchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (canDelete) {
                        val position = viewHolder.adapterPosition
                        viewModel.deleteUser(
                            (binding.userRecyclerView.adapter as UserListElementAdapter).data[position]
                        )
                    } else {
                        Toast.makeText(context, "Access Denied", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun getSwipeDirs(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    if (!canDelete) return 0
                    return super.getSwipeDirs(recyclerView, viewHolder)
                }
            })
        touchHelper.attachToRecyclerView(binding.userRecyclerView)
        binding.floatingActionButton2.setOnClickListener {
            if (isFabOpen)
                closeFabMenu()
            else
                openFabMenu()
            isFabOpen = !isFabOpen
        }
        binding.fabVk.setOnClickListener {
            Repository.OAUTH.Vk.provider.auth { bundle ->
                (activity as MainActivity).navHost.navController.navigate(
                    R.id.action_userList_to_webFragment,
                    bundle
                )
            }
        }
        binding.fabLogin.setOnClickListener {
            val bundle = Bundle()

            bundle.putString(
                "mail",
                "mail"
            )
            bundle.putString(
                "nick",
                "name"
            )
            bundle.putInt(
                "role_level",
                UserInfo.Roles.USER.roleLevel
            )
            findNavController().navigate(R.id.action_userList_to_addUser, bundle)
        }
        binding.fabGoogle.setOnClickListener {
            Repository.OAUTH.Google.provider.auth { bundle ->
                (activity as MainActivity).navHost.navController.navigate(
                    R.id.action_userList_to_webFragment,
                    bundle
                )
            }
        }

        RecyclerItemClickListener.registerListener(context, binding.userRecyclerView,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val data =
                        (binding.userRecyclerView.adapter as UserListElementAdapter).data
                    if (position in data.indices) {
                        Repository.activeUserId = data[position].userId
                        setCanDelete()
                        Toast.makeText(
                            context,
                            "Selected user: ${data[position].userId}",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(context, "Invalid position: $position", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("ProblemListFragment/click", "Invalid position: $position")
                    }
                }

                override fun onLongItemClick(view: View?, position: Int) {}
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)

        viewModel.getUsersList().observe(viewLifecycleOwner, {
            binding.userRecyclerView.adapter = UserListElementAdapter(it)
        })
        setCanDelete()
    }

    private fun openFabMenu() {
        binding.fabGoogle.animate().translationY(-resources.getDimension(R.dimen.dp_210))
        binding.fabVk.animate().translationY(-resources.getDimension(R.dimen.dp_140))
        binding.fabLogin.animate().translationY(-resources.getDimension(R.dimen.dp_70))
    }

    private fun closeFabMenu() {
        binding.fabGoogle.animate().translationY(0F)
        binding.fabVk.animate().translationY(0F)
        binding.fabLogin.animate().translationY(0F)
    }

    private fun setCanDelete() {
        Repository.activeUserId?.let {
            Repository.usersRepository.getUser<UserInfoDTO>(it)
                .observe(viewLifecycleOwner, { user ->
                    if (user != null) {
                        canDelete = user.roleLevel >= UserInfo.Roles.MODERATOR.roleLevel
                    }
                })
        }

    }
}
