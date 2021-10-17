package dev.ky3he4ik.chessproblems.presentation.view.problems.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.databinding.ProblemPositionListItemBinding

class AddProblemPositionListItemAdapter :
    RecyclerView.Adapter<AddProblemPositionListItemAdapter.ProblemPositionHolder>() {
    val data = MutableLiveData<ArrayList<String>>(arrayListOf())

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
        val positions = data.value ?: return
        positionHolder.binding.position.setText(positions[position])
        positionHolder.binding.position.addTextChangedListener {
            data.value?.set(position, it.toString())
        }
    }

    fun addSection(pos: String = "") {
        data.value?.add(pos) ?: return
        notifyItemInserted(data.value!!.size - 1)
    }

    fun clear() {
        if (data.value == null)
            data.value = arrayListOf()
        val size = data.value!!.size
        data.value?.clear()
        notifyItemRangeRemoved(0, size)
    }

    class ProblemPositionHolder(val binding: ProblemPositionListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}