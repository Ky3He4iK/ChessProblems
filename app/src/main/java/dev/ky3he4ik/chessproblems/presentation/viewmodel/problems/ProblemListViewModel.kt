package dev.ky3he4ik.chessproblems.presentation.viewmodel.problems

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.presentation.repository.Repository
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo

class ProblemListViewModel : ViewModel() {
    fun getProblemsListLive(lifecycleOwner: LifecycleOwner): LiveData<List<ProblemInfo>> =
        Repository.problemsRepository.getAllProblemsLive(lifecycleOwner)

    fun deleteProblem(problem: ProblemInfo) =
        Repository.problemsRepository.deleteProblem(problem)

    fun getRandomProblem(): LiveData<ProblemInfo?> = Repository.chessBlunders.getRandomProblem()

    fun addProblem(problem: ProblemInfo) = Repository.problemsRepository.addProblem(problem)
}
