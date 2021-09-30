package dev.ky3he4ik.chessproblems.presentation.view.problems.adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.databinding.ProblemListElementBinding
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.operations.ProblemOperations
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemInfoDTO

class ProblemListElementAdapter(val data: List<ProblemInfo>) :
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
        if (problemInfo.image != null) {
            try {
                holder.binding.image.setImageBitmap(
                    BitmapFactory.decodeFileDescriptor(
                        holder.binding.root.context.contentResolver.openFileDescriptor(
                            Uri.parse(problemInfo.image), "r"
                        )?.fileDescriptor
                    )
                )
            } catch (e: Exception) {
                holder.binding.image.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
                Log.e("Chess/PLEA", e.toString(), e)
            }
        }
        holder.binding.shareImage.setOnClickListener {
            val encoded = ProblemOperations.toUrl(problemInfo)
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "https://c.ky3he4ik.dev/p/?$encoded"
            )
            sendIntent.type = "text/plain"
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(it.context, shareIntent, null)
        }
    }

    class ProblemListElementHolder(val binding: ProblemListElementBinding) :
        RecyclerView.ViewHolder(binding.root)
}
