package dev.ky3he4ik.chessproblems.presentation.viewmodel.users

import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository
import dev.ky3he4ik.chessproblems.presentation.repository.mock.MockUsersRepository

class AddUserViewModel : ViewModel() {
    fun addUser(user: UserInfo) = Repository.usersRepository.addUser(user)
}
