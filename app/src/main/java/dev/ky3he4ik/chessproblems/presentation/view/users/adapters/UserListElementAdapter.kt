package dev.ky3he4ik.chessproblems.presentation.view.users.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.databinding.UserListElementBinding
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import java.lang.StringBuilder

class UserListElementAdapter(val data: List<UserInfo>): RecyclerView.Adapter<UserListElementAdapter.UserListElementHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListElementHolder {
        val binding: UserListElementBinding =
            UserListElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UserListElementHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListElementHolder, position: Int) {
        val userInfo = data[position]
        holder.binding.userId.text = "User #" + userInfo.userId
        holder.binding.nickname.text = "Nick: " + userInfo.nick
        holder.binding.rating.text = "Rating: " + userInfo.rating.toString()
        holder.binding.solved.text = "Solved: " + userInfo.solved.toString()
        val sb = StringBuilder("Solved problems:")
        userInfo.solvedProblems.forEach { sb.append("\n${it.problem_id} - ${it.solvingTime}s") }
        holder.binding.solvedProblems.text = sb
    }

    class UserListElementHolder(val binding: UserListElementBinding) :
        RecyclerView.ViewHolder(binding.root)
}