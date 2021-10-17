package dev.ky3he4ik.chessproblems.presentation.repository.network.chessblunders

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
        return ProblemOperations.fromFen(
            fenBefore,
            ProblemInfo(
                0, "Blunder #${id}", null, "", elo,
                true, forcedLine, arrayListOf()
            )
        )
    }
}
