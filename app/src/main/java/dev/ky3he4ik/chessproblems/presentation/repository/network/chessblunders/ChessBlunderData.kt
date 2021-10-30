package dev.ky3he4ik.chessproblems.presentation.repository.network.chessblunders

import android.util.Log
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.operations.ProblemOperations

data class ChessBlunderData(
    val blunderMove: String,
    val elo: Int,
    val fenBefore: String,
    val forcedLine: List<String>,
    val game_id: String,
    val id: String,
    val move_index: Int,
) {
    fun toProblemInfo(): ProblemInfo? {
        val problem =  ProblemOperations.fromFenWithMoves(
            fenBefore,
            forcedLine,
            ProblemInfo(
                0, "Blunder #${id}", null, fenBefore
                , elo,
                true, listOf(), arrayListOf()
            )
        )
        if (problem == null)
            Log.e("ChessBlunder/parse", "$fenBefore ${forcedLine.joinToString()}")
        return problem
    }
}
