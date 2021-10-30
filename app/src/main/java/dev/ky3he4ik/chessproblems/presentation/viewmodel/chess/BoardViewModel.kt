package dev.ky3he4ik.chessproblems.presentation.viewmodel.chess

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class BoardViewModel : ViewModel() {
    fun getProblemInfo(problemId: Int): LiveData<ProblemInfo?> =
        Repository.problemsRepository.getProblem(problemId)

    fun registerProblemSolved(problemId: Int, userId: Int, time: Long) {
//        TODO()
    }
}