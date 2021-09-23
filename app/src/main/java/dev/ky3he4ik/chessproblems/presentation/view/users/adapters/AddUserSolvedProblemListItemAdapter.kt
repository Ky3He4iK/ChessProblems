package dev.ky3he4ik.chessproblems.presentation.view.users.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.databinding.UserSolvedProblemListItemBinding

class AddUserSolvedProblemListItemAdapter :
    RecyclerView.Adapter<AddUserSolvedProblemListItemAdapter.UserSolvedProblemHolder>() {

    private val _data = MutableLiveData<ArrayList<Pair<Int, Long>>>()
    val data: LiveData<ArrayList<Pair<Int, Long>>>
        get() = _data

    init {
        _data.value = arrayListOf()
    }

    override fun getItemCount(): Int {
        return data.value?.size ?: 0
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserSolvedProblemHolder {
        val binding: UserSolvedProblemListItemBinding =
            UserSolvedProblemListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UserSolvedProblemHolder(binding)
    }

    override fun onBindViewHolder(holder: UserSolvedProblemHolder, position: Int) {
        val problem = data.value!![position]
        holder.binding.solvedProblemId.setText(problem.first.toString())
        holder.binding.solvedProblemId.addTextChangedListener {
            val id = it?.toString()?.toIntOrNull()
            if (id != null)
                _data.value?.set(position, Pair(id, _data.value!![position].second))
        }
        holder.binding.solvedProblemTime.setText(problem.second.toString())
        holder.binding.solvedProblemTime.addTextChangedListener {
            val time = it?.toString()?.toLongOrNull()
            if (time != null)
                _data.value?.set(position, Pair(_data.value!![position].first, time))
        }
    }

    fun addSection() {
        _data.value?.add(Pair(0, 0))
        notifyItemInserted(_data.value!!.size - 1)
    }

    class UserSolvedProblemHolder(val binding: UserSolvedProblemListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}