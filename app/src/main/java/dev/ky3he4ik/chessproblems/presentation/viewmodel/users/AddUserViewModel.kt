package dev.ky3he4ik.chessproblems.presentation.viewmodel.users

import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class AddUserViewModel : ViewModel() {
    fun addUser(
        nick: String,
        rating: Int,
        solvedProblems: List<Pair<Int, Long>>,
    ) {
        val solvedProblemsModel = solvedProblems.map { SolvedProblem(it.first, it.second) }
        val user = UserInfo(0, nick, rating, solvedProblems.size, solvedProblemsModel)

        Repository.getUsersRepository().addUser(user)
    }
}
