package dev.ky3he4ik.chessproblems.presentation.view.problems.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.databinding.ProblemMovesListItemBinding

class AddProblemMovesListItemAdapter :
    RecyclerView.Adapter<AddProblemMovesListItemAdapter.ProblemMovesHolder>() {
    val data = MutableLiveData<ArrayList<String>>(arrayListOf())

    override fun getItemCount(): Int {
        return data.value?.size ?: 0
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProblemMovesHolder {
        val binding: ProblemMovesListItemBinding =
            ProblemMovesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ProblemMovesHolder(binding)
    }

    override fun onBindViewHolder(movesHolder: ProblemMovesHolder, position: Int) {
        val moves = data.value ?: return
        movesHolder.binding.move.setText(moves[position])
        movesHolder.binding.move.addTextChangedListener {
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

    class ProblemMovesHolder(val binding: ProblemMovesListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}