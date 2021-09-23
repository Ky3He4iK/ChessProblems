package dev.ky3he4ik.chessproblems.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.ky3he4ik.chessproblems.presentation.viewmodel.users.UserViewModel

class UserViewModelFactory(private val userId: Int): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = UserViewModel(userId) as T
}
