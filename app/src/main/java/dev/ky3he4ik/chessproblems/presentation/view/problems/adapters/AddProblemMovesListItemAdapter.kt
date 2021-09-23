package dev.ky3he4ik.chessproblems.presentation.view.problems.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.databinding.ProblemMovesListItemBinding

class AddProblemMovesListItemAdapter: RecyclerView.Adapter<AddProblemMovesListItemAdapter.ProblemMovesHolder>() {
    private val _data = MutableLiveData<ArrayList<Pair<String, String>>>()
    val data: LiveData<ArrayList<Pair<String, String>>>
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
        val move = data.value!![position]
        movesHolder.binding.movesFrom.setText(move.first)
        movesHolder.binding.movesFrom.addTextChangedListener {
            _data.value?.set(position, Pair(it.toString(), _data.value!![position].second))
        }
        movesHolder.binding.movesTo.setText(move.second)
        movesHolder.binding.movesTo.addTextChangedListener {
            _data.value?.set(position, Pair(_data.value!![position].first, it.toString()))
        }
    }

    fun addSection() {
        _data.value?.add(Pair("", ""))
        notifyItemInserted(_data.value!!.size - 1)
    }

    class ProblemMovesHolder(val binding: ProblemMovesListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}