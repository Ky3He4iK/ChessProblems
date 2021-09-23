package dev.ky3he4ik.chessproblems.presentation.viewmodel.problems

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class ProblemListViewModel: ViewModel() {
    fun getProblemsList(): LiveData<List<ProblemInfo>> {
        return Repository.getProblemsRepository().getAllProblems()
    }

    fun deleteProblem(problem: ProblemInfo) {
        Repository.getProblemsRepository().deleteProblem(problem)
    }
}
