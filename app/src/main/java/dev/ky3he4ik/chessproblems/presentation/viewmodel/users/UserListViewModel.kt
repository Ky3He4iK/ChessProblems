package dev.ky3he4ik.chessproblems.presentation.viewmodel.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class UserListViewModel: ViewModel() {
    fun getUsersList(): LiveData<List<UserInfo>> {
        return Repository.getUsersRepository().getAllUsers()
    }

    fun deleteUser(user: UserInfo) {
        Repository.getUsersRepository().deleteUser(user)
    }
}
