package dev.ky3he4ik.chessproblems.presentation.viewmodel.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo

class AddUserViewModel : ViewModel() {
    fun addUser(user: UserInfo) = Repository.usersRepository.addUser(user)

    fun getAllProblems() = Repository.problemsRepository.getAllProblems<ProblemInfo>()
    private val _image = MutableLiveData<String?>()
    val image: LiveData<String?>
        get() = _image

    fun setImage(image: String?) {
        _image.value = image
    }
}
