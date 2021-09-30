package dev.ky3he4ik.chessproblems.domain.operations

import android.util.Log
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove
import java.net.URLDecoder
import java.net.URLEncoder

object ProblemOperations {
    fun toUrl(problemInfo: ProblemInfo): String {
        val sb = StringBuilder().append("t=")
            .append(URLEncoder.encode(problemInfo.title, "utf-8"))
            .append("&d=").append(URLEncoder.encode(problemInfo.description, "utf-8"))
            .append("&df=").append(problemInfo.difficulty).append("&ws=")
        sb.append(if (problemInfo.whiteStarts) 1 else 0)
        sb.append("&m=")
        problemInfo.moves.forEach {
            sb.append(it.posStart).append('-').append(it.posEnd).append('_')
        }
        sb.deleteAt(sb.length - 1)
        sb.append("&p=")
        problemInfo.figurePosition.forEach {
            sb.append(if (it.isWhite) 1 else 0).append("-").append(it.code).append('_')
        }
        return sb.deleteAt(sb.length - 1).toString()
    }

    fun fromUrl(url: String): ProblemInfo? {
        var startPos = url.indexOf('?')
        if (startPos == -1)
            startPos = 0
        val content = url.substring(startPos).split("&")
        val problemInfo = ProblemInfo(0, "", null, "", 0, false, arrayListOf(), arrayListOf())
        content.forEach {
            val splitPos = it.indexOf('=')
            if (splitPos != -1) {
                val parts = it.split('=', limit =  2)
                try {
                    when (parts[0]) {
                        "t" -> problemInfo.title = URLDecoder.decode(parts[1], "utf-8")
                        "d" -> problemInfo.description = URLDecoder.decode(parts[1], "utf-8")
                        "df" -> problemInfo.difficulty =
                            parts[1].toIntOrNull() ?: problemInfo.difficulty
                        "ws" -> problemInfo.whiteStarts = parts[1] == "1"
                        "m" -> {
                            val moves = parts[1].split('_')
                            moves.forEach { move ->
                                val mp = move.split('-')
                                if (mp.size == 2)
                                    problemInfo.moves += ProblemMove(mp[0], mp[1])
                            }
                        }
                        "p" -> {
                            val positions = parts[1].split('_')
                            positions.forEach { position ->
                                val pos = position.split('-')
                                if (pos.size == 2)
                                    problemInfo.figurePosition += FigurePosition(
                                        pos[0] == "1",
                                        pos[1]
                                    )
                            }
                        }
                        else -> Log.w("Chess/PO", "Unknown code: $it")
                    }
                } catch (e: Exception) {
                    Log.e("Chess/PO", e.toString(), e)
                }
            }
        }
        return problemInfo
    }
}
