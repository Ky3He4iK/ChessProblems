package dev.ky3he4ik.chessproblems.presentation.view.users.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.UserListElementBinding
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo

class UserListElementAdapter(val data: List<UserInfo>) :
    RecyclerView.Adapter<UserListElementAdapter.UserListElementHolder>() {

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
        holder.binding.mail.text = "Mail: " + userInfo.mail
        val role = UserInfo.Roles.values().sortedByDescending { it.roleLevel }.firstOrNull {
            it.roleLevel >= userInfo.roleLevel
        }
        holder.binding.role.text = "Role: " + role.toString()
        val sb = StringBuilder("Solved problems:")
        userInfo.solvedProblems.forEach { sb.append("\n${it.problemId} - ${it.solvingTime / 1000}s") }
        holder.binding.solvedProblems.text = sb
        if (userInfo.image != null) {
            try {
                holder.binding.image.setImageBitmap(
                    BitmapFactory.decodeFileDescriptor(
                        holder.binding.root.context.contentResolver.openFileDescriptor(
                            Uri.parse(userInfo.image), "r"
                        )?.fileDescriptor
                    )
                )
            } catch (e: Exception) {
                holder.binding.image.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
                Log.e("Chess/PLEA", e.toString(), e)
            }
        }
    }

    class UserListElementHolder(val binding: UserListElementBinding) :
        RecyclerView.ViewHolder(binding.root)
}