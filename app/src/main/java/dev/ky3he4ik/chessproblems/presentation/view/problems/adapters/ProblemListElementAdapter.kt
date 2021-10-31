package dev.ky3he4ik.chessproblems.presentation.view.problems.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.ProblemListElementBinding
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.operations.ProblemOperations
import kotlin.coroutines.coroutineContext

class ProblemListElementAdapter(val data: List<ProblemInfo>, val context: Context) :
    RecyclerView.Adapter<ProblemListElementAdapter.ProblemListElementHolder>() {
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
            sb.append('\n').append(it.move)
        }
        holder.binding.moves.text = sb.toString()
        sb.clear().append("White figures:")
        val sbB = StringBuilder("\n\nBlack figures:")
        problemInfo.figurePosition.forEach {
            val s = "\n${it.figure ?: ' '}${it.letter}${it.number}"
            if (it.isWhite)
                sb.append(s)
            else
                sbB.append(s)
        }
        sb.append(sbB)
        holder.binding.positions.text = sb.toString()
//        holder.binding.shareImage.setOnClickListener {
//            Toast.makeText(context, "Temporary not working", Toast.LENGTH_SHORT).show()
//            return@setOnClickListener
//
//            val encoded = ProblemOperations.toUrl(problemInfo)
//            val sendIntent = Intent(Intent.ACTION_SEND)
//            sendIntent.putExtra(
//                Intent.EXTRA_TEXT,
//                "https://c.ky3he4ik.dev/p/?$encoded"
//            )
//            sendIntent.type = "text/plain"
//            val shareIntent = Intent.createChooser(sendIntent, null)
//            startActivity(it.context, shareIntent, null)
//        }
    }

    class ProblemListElementHolder(val binding: ProblemListElementBinding) :
        RecyclerView.ViewHolder(binding.root)
}
