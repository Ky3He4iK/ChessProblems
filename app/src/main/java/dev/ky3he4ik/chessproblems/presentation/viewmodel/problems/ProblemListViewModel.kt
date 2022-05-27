package dev.ky3he4ik.chessproblems.presentation.viewmodel.problems

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.presentation.repository.Repository
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.domain.operations.UserOperations

class ProblemListViewModel : ViewModel() {
    fun getSolvedProblemsListLive(lifecycleOwner: LifecycleOwner): LiveData<List<ProblemInfo>> =
        Repository.problemsRepository.getSolvedProblemsLive(lifecycleOwner)

    fun getUnsolvedProblemsListLive(lifecycleOwner: LifecycleOwner): LiveData<List<ProblemInfo>> =
        Repository.problemsRepository.getUnsolvedProblemsLive(lifecycleOwner)

    fun deleteProblem(problem: ProblemInfo) =
        Repository.problemsRepository.deleteProblem(problem)

    fun getRandomProblem(): LiveData<ProblemInfo?> = Repository.chessBlunders.getRandomProblem()

    fun addProblem(problem: ProblemInfo) = Repository.problemsRepository.addProblem(problem)

    fun canAddProblems(userInfo: UserInfo) = UserOperations.canAddProblems(userInfo)

    fun getActiveUserId() = Repository.activeUserId

    fun getUser(userId: Int): LiveData<UserInfo?> = Repository.usersRepository.getUser(userId)
}
