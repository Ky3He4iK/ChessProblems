package dev.ky3he4ik.chessproblems.presentation.viewmodel.problems

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class AddProblemViewModel : ViewModel() {
    fun addProblem(problem: ProblemInfo) = Repository.problemsRepository.addProblem(problem)

    fun getRandomProblem(): LiveData<ProblemInfo?> = Repository.chessBlunders.getRandomProblem()
}
