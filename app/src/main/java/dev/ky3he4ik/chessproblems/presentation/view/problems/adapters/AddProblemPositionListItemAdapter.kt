package dev.ky3he4ik.chessproblems.presentation.view.problems.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.databinding.ProblemPositionListItemBinding

class AddProblemPositionListItemAdapter: RecyclerView.Adapter<AddProblemPositionListItemAdapter.ProblemPositionHolder>() {
    private val _data = MutableLiveData<ArrayList<String>>()
    val data: LiveData<ArrayList<String>>
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
    ): ProblemPositionHolder {
        val binding: ProblemPositionListItemBinding =
            ProblemPositionListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ProblemPositionHolder(binding)
    }

    override fun onBindViewHolder(positionHolder: ProblemPositionHolder, position: Int) {
        val pos = data.value!![position]
        positionHolder.binding.position.setText(pos)
        positionHolder.binding.position.addTextChangedListener {
            _data.value?.set(position, it.toString())
        }
    }

    fun addSection() {
        _data.value?.add("")
        notifyItemInserted(_data.value!!.size - 1)
    }

    class ProblemPositionHolder(val binding: ProblemPositionListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}