package dev.ky3he4ik.chessproblems.presentation.viewmodel.problems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class AddProblemViewModel : ViewModel() {
    fun addProblem(problem: ProblemInfo) = Repository.problemsRepository.addProblem(problem)
    private val _image = MutableLiveData<String?>()
    val image: LiveData<String?>
        get() = _image

    fun setImage(image: String?) {
        _image.value = image
    }

    fun getRandomProblem(): LiveData<ProblemInfo?> {
        return Repository.chessBlunders.getRandomProblem()
    }
}
