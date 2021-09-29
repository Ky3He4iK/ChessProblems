package dev.ky3he4ik.chessproblems.presentation.view.users.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        val sb = StringBuilder("Solved problems:")
        userInfo.solvedProblems.forEach { sb.append("\n${it.problem_id} - ${it.solvingTime}s") }
        holder.binding.solvedProblems.text = sb
        if (userInfo.image != null) {
            try {
                val resId = userInfo.image?.toIntOrNull()
                if (resId == null) {
                    holder.binding.image.setImageBitmap(
                        BitmapFactory.decodeFileDescriptor(
                            holder.binding.root.context.contentResolver.openFileDescriptor(
                                Uri.parse(userInfo.image), "r"
                            )?.fileDescriptor
                        )
                    )
                } else
                    holder.binding.image.setImageResource(resId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class UserListElementHolder(val binding: UserListElementBinding) :
        RecyclerView.ViewHolder(binding.root)
}