package dev.ky3he4ik.chessproblems

import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove
import dev.ky3he4ik.chessproblems.domain.operations.ProblemOperations
import org.junit.Assert.assertEquals
import org.junit.Test

class ProblemParseTests {
    @Test
    fun parsing_isCorrect() {
        val problem = ProblemOperations.fromFenWithMoves(
            "8/ppp1Qp1k/4q1p1/7p/2r5/P3PpPP/1P1R1P1K/8 w - - 2 32",
            listOf("Rc1", "Qxf7+", "Qxf7"),
            ProblemInfo(
                0, "Blunder #1", "descr", 1291,
                true, listOf(), listOf()
            )
        )
        val expected = ProblemInfo(1, "Blunder #1", "descr", 1291, false,
            listOf(
                ProblemMove(2, 4, 2, 0, null, false, "Rc1"),
                ProblemMove(2, 0, 5, 6, null, false, "Qxf7"),
                ProblemMove(4, 5, 5, 6, null, false, "Qxf7+"),
            ),
            listOf(
                FigurePosition('e', 6, 'Q', true),
            )
        )

    }
}