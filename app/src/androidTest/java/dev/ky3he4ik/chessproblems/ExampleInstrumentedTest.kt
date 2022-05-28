package dev.ky3he4ik.chessproblems

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove
import dev.ky3he4ik.chessproblems.domain.operations.ProblemOperations
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun canParseProblem() {
        val problem = ProblemOperations.fromFenWithMoves(
            "8/ppp1Qp1k/4q1p1/7p/2r5/P3PpPP/1P1R1P1K/8 w - - 2 32",
            listOf("Rc1", "Qxf7+", "Qxf7"),
            ProblemInfo(
                1, "Blunder #1", "descr", 1291,
                true, listOf(), listOf()
            )
        )
        val expected = ProblemInfo(
            1, "Blunder #1", "descr", 1291, false,
            listOf(
                ProblemMove(2, 3, 2, 0, null, false, "Rc1"),
                ProblemMove(4, 6, 5, 6, null, false, "Qxf7+"),
                ProblemMove(4, 5, 5, 6, null, false, "Qxf7"),
            ),
            listOf(
                FigurePosition('a', 6, null, false),
                FigurePosition('b', 6, null, false),
                FigurePosition('c', 6, null, false),
                FigurePosition('e', 6, 'Q', true),
                FigurePosition('f', 6, null, false),
                FigurePosition('h', 6, 'K', false),
                FigurePosition('e', 5, 'Q', false),
                FigurePosition('g', 5, null, false),
                FigurePosition('h', 4, null, false),
                FigurePosition('c', 3, 'R', false),
                FigurePosition('a', 2, null, true),
                FigurePosition('e', 2, null, true),
                FigurePosition('f', 2, null, false),
                FigurePosition('g', 2, null, true),
                FigurePosition('h', 2, null, true),
                FigurePosition('b', 1, null, true),
                FigurePosition('d', 1, 'R', true),
                FigurePosition('f', 1, null, true),
                FigurePosition('h', 1, 'K', true),
            )
        )
        assertEquals(problem?.problemId, expected.problemId)
        assertEquals(problem?.description, expected.description)
        assertEquals(problem?.difficulty, expected.difficulty)
        assertEquals(problem?.title, expected.title)
        assertEquals(problem?.whiteStarts, expected.whiteStarts)

        assertEquals(
            problem?.figurePosition?.map { "${it.figure} ${it.letter} ${it.number} ${it.isWhite}" },
            expected.figurePosition.map { "${it.figure} ${it.letter} ${it.number} ${it.isWhite}" })
        assertEquals(
            problem?.moves?.map { "${it.move} ${it.letterStart}${it.numberStart} ${it.letterEnd}${it.numberEnd} ${it.promotion} ${it.isCastling}" },
            expected.moves.map { "${it.move} ${it.letterStart}${it.numberStart} ${it.letterEnd}${it.numberEnd} ${it.promotion} ${it.isCastling}" },
        )
    }

    @Test
    fun canOpenProblem() {

    }
}