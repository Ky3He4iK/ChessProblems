package dev.ky3he4ik.chessproblems.presentation.viewmodel.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.presentation.repository.Repository
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo

class UserListViewModel : ViewModel() {
    fun getUsersList(): LiveData<List<UserInfo>> =
        Repository.usersRepository.getAllUsers()

    fun deleteUser(user: UserInfo) = Repository.usersRepository.deleteUser(user)
}
