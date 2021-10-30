package dev.ky3he4ik.chessproblems.presentation.repository

import androidx.lifecycle.LiveData
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo

interface UsersRepository {
    fun <T : UserInfo> getAllUsers(): LiveData<List<T>>
    fun <T : UserInfo> addUser(user: T)
    fun <T : UserInfo> getUser(userId: Int): LiveData<T?>
    fun <T : UserInfo> deleteUser(user: T)
    fun addSolvedProblem(userId: Int, solvedProblem: SolvedProblem)
}