package dev.ky3he4ik.chessproblems.domain.operations

import android.util.Log
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import java.net.URLDecoder
import java.net.URLEncoder

object ProblemOperations {
    fun toUrl(problemInfo: ProblemInfo): String {
        val sb = StringBuilder().append("t=")
            .append(urlencode(problemInfo.title))
            .append("&d=").append(urlencode(problemInfo.description))
            .append("&df=").append(problemInfo.difficulty).append("&ws=")
        sb.append(if (problemInfo.whiteStarts) 1 else 0)
        sb.append("&m=")
        problemInfo.moves.forEach {
            sb.append(urlencode(it)).append('_')
        }
        sb.deleteAt(sb.length - 1)
        sb.append("&p=")
        problemInfo.figurePosition.forEach {
            sb.append(if (it.isWhite) 1 else 0).append(it.code).append('_')
        }
        return sb.deleteAt(sb.length - 1).toString()
    }

    fun fromUrl(url: String): ProblemInfo? {
        val content = url.substring(url.indexOf('?') + 1).split("&")
        val problemInfo = ProblemInfo(0, "", null, "", 0, false, arrayListOf(), arrayListOf())
        var counter = 0 // at least 2 fields of 6 to parse
        content.forEach {
            val splitPos = it.indexOf('=')
            if (splitPos == -1)
                return@forEach

            val parts = it.split('=', limit = 2)
            ++counter
            try {
                when (parts[0]) {
                    "t" -> problemInfo.title = urldecode(parts[1])
                    "d" -> problemInfo.description = urldecode(parts[1])
                    "df" -> problemInfo.difficulty =
                        parts[1].toIntOrNull() ?: problemInfo.difficulty
                    "ws" -> problemInfo.whiteStarts = parts[1] == "1"
                    "m" -> {
                        problemInfo.moves += parts[1].split('_').map { m -> urldecode(m) }
                    }
                    "p" -> {
                        val positions = parts[1].split('_')
                        positions.forEach { position ->
                            if (position.length > 1)
                                problemInfo.figurePosition += FigurePosition(
                                    position[0] == '1',
                                    urlencode(position.substring(1))
                                )
                        }
                    }
                    else -> {
                        Log.w("Chess/PO", "Unknown code: $it")
                        --counter
                    }
                }
            } catch (e: Exception) {
                --counter
                Log.e("Chess/PO", e.toString(), e)
            }
        }
        if (counter < 2)
            return null
        return problemInfo
    }

    fun fromFen(
        fenString: String,
        baseProblem: ProblemInfo
    ): ProblemInfo? {
        val fenInfo = fenString.split(' ')
        if (fenInfo.size != 6)
            return null

        val linedPositions = fenInfo[0].split('/')

        if (linedPositions.size != 8)
            return null
        for (i in 0 until 8) {
            val line = 8 - i
            var letterNum = 0
            for (pos in linedPositions[i]) {
                if ('0'.code <= pos.code && pos.code <= '9'.code)
                    letterNum += pos.digitToInt()
                else {
                    val isWhite = pos.code < 'a'.code
                    var position = pos.uppercase()
                    if (position == "P")
                        position = ""
                    position += Char('a'.code + letterNum)
                    position += line
                    baseProblem.figurePosition += FigurePosition(isWhite, position)
                    letterNum++
                }
            }
        }
        baseProblem.whiteStarts = fenInfo[1] == "w"
        // todo: castling for white and black (fenInfo[2])
        // todo: castling for pawn moves (fenInfo[3])
        // todo: 50 moves rule (fenInfo[4])
        // todo: move number (fenInfo[5])

        return baseProblem
    }

    private fun urlencode(string: String): String =
        URLEncoder.encode(string.replace("&", "%FF"), "utf-8")

    private fun urldecode(string: String): String =
        URLDecoder.decode(string.replace("%FF", "&"), "utf-8")

}
