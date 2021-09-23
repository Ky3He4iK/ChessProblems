package dev.ky3he4ik.chessproblems.presentation.viewmodel.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class UserViewModel(userId: Int) : ViewModel() {
    private val _user = MutableLiveData<UserInfo>()
    val user: LiveData<UserInfo>
        get() = _user

    init {
        _user.value = Repository.getUsersRepository().getUser(userId)
    }
}
