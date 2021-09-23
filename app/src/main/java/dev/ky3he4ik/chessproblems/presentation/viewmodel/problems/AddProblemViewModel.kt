package dev.ky3he4ik.chessproblems.presentation.viewmodel.problems

import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class AddProblemViewModel : ViewModel() {
    fun addProblem(
        title: String,
        description: String,
        difficulty: Int,
        whiteStarts: Boolean,
        movesRaw: List<Pair<String, String>>,
        whitePositions: List<String>,
        blackPositions: List<String>,
    ) {
        val moves = movesRaw.map { ProblemMove(it.first, it.second) }
        val positions = whitePositions.map { FigurePosition(true, it) } +
                blackPositions.map { FigurePosition(false, it) }
        val problem = ProblemInfo(0, title, description, difficulty, whiteStarts, moves, positions)
        Repository.getProblemsRepository().addProblem(problem)
    }
}