package dev.ky3he4ik.chessproblems.presentation.viewmodel.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.domain.operations.UserOperations
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class UserListViewModel : ViewModel() {
    fun getUsersList(): LiveData<List<UserInfo>> =
        Repository.usersRepository.getAllUsers()

    fun getActiveUser(): LiveData<UserInfo?>? = Repository.activeUserId?.let {
        Repository.usersRepository.getUser(it)
    }

    fun deleteUser(user: UserInfo) = Repository.usersRepository.deleteUser(user)

    fun canDeleteUsers(userInfo: UserInfo) = UserOperations.canDeleteUsers(userInfo)
}
