package dev.ky3he4ik.chessproblems.presentation.view.problems.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.databinding.ProblemListElementBinding
import dev.ky3he4ik.chessproblems.databinding.UserListElementBinding
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.presentation.view.problems.ProblemList
import java.lang.StringBuilder

class ProblemListElementAdapter(val data: List<ProblemInfo>): RecyclerView.Adapter<ProblemListElementAdapter.ProblemListElementHolder>() {
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProblemListElementHolder {
        val binding: ProblemListElementBinding =
            ProblemListElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ProblemListElementHolder(binding)
    }

    override fun onBindViewHolder(holder: ProblemListElementHolder, position: Int) {
        val problemInfo = data[position]
        holder.binding.problemId.text = "Problem #" + problemInfo.problemId.toString()
        holder.binding.title.text = "Title: " + problemInfo.title
        holder.binding.description.text = "Description: " + problemInfo.description
        holder.binding.difficulty.text = "Difficulty:" + problemInfo.difficulty.toString()
        holder.binding.whiteStarts.text = "WhiteStarts: " + problemInfo.whiteStarts.toString()
        val sb = StringBuilder("Moves:")
        problemInfo.moves.forEach {
            sb.append('\n').append(it.posStart).append(" - ").append(it.posEnd)
        }
        holder.binding.moves.text = sb.toString()
        sb.clear().append("White figures:")
        val sbB = StringBuilder("\n\nBlack figures:")
        problemInfo.figurePosition.forEach {
            if (it.isWhite)
                sb.append('\n').append(it.code)
            else
                sbB.append('\n').append(it.code)
        }
        sb.append(sbB)
        holder.binding.positions.text = sb.toString()
    }

    class ProblemListElementHolder(val binding: ProblemListElementBinding) :
        RecyclerView.ViewHolder(binding.root)
}
