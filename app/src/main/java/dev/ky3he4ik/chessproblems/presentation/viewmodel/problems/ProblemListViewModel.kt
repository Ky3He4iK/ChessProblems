package dev.ky3he4ik.chessproblems.presentation.viewmodel.problems

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.presentation.repository.Repository
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo

class ProblemListViewModel : ViewModel() {
    fun getProblemsList(): LiveData<List<ProblemInfo>> =
        Repository.problemsRepository.getAllProblems()

    fun deleteProblem(problem: ProblemInfo) =
        Repository.problemsRepository.deleteProblem(problem)
}
