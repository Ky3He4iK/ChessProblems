package dev.ky3he4ik.chessproblems.presentation.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove
import dev.ky3he4ik.chessproblems.presentation.repository.ProblemsRepository

class MockProblemsRepository : ProblemsRepository {
    private var data: MutableLiveData<List<ProblemInfo>>
    private var list: MutableList<ProblemInfo>

    init {
        list = arrayListOf(
            ProblemInfo(
                1,
                "Eat the horse",
                "Act like you're from Kazakhstan",
                1,
                true,
                listOf(
                    ProblemMove("c1", "c5")
                ),
                listOf(
                    FigurePosition(true, "a2"),
                    FigurePosition(true, "e3"),
                    FigurePosition(true, "f2"),
                    FigurePosition(true, "g2"),
                    FigurePosition(true, "h2"),
                    FigurePosition(true, "Rc1"),
                    FigurePosition(true, "Rh1"),
                    FigurePosition(true, "Nd6"),
                    FigurePosition(true, "Nf3"),
                    FigurePosition(true, "Ke2"),
                    FigurePosition(false, "a4"),
                    FigurePosition(false, "b4"),
                    FigurePosition(false, "c6"),
                    FigurePosition(false, "f7"),
                    FigurePosition(false, "f6"),
                    FigurePosition(false, "h7"),
                    FigurePosition(false, "Ra8"),
                    FigurePosition(false, "Rh8"),
                    FigurePosition(false, "Nc5"),
                    FigurePosition(false, "Nh6"),
                    FigurePosition(false, "Kf8"),
                )
            )
        )

        data = MutableLiveData(list)
    }

    override fun <T : ProblemInfo> getAllProblems(): LiveData<List<T>> {
        return data as LiveData<List<T>>
    }

    override fun <T : ProblemInfo> addProblem(problem: T) {
        list += problem
    }

    override fun <T : ProblemInfo> getProblem(problemId: Int): T? {
        return list.find { it.problemId == problemId } as T?
    }

    override fun <T : ProblemInfo> deleteProblem(problem: T) {
        list -= problem
    }
}