package dev.ky3he4ik.chessproblems.presentation.repository.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.repository.UsersRepository
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.SolvedProblemDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserInfoDTO
import dev.ky3he4ik.chessproblems.presentation.repository.room.dao.UsersDAO

class UsersRepositoryImpl(application: Application) : UsersRepository {
    private var usersDAO: UsersDAO

    init {
        val db: ChessDatabase = ChessDatabase.getInstance(application)
        usersDAO = db.usersDAO()
    }

    override fun <T : UserInfo> getAllUsers(): LiveData<List<T>> {
        val data = MutableLiveData<List<UserInfoDTO>>()
        ChessDatabase.databaseWriteExecutor.execute {
            data.postValue(usersDAO.getAllUsers())
        }
        return data as LiveData<List<T>>
    }

    override fun <T : UserInfo> addUser(user: T) {
        ChessDatabase.databaseWriteExecutor.execute {
            usersDAO.addUser(
                UserInfoDTO(user)
            )
        }
    }

    override fun <T : UserInfo> getUser(userId: Int): LiveData<T?> {
        val data = MutableLiveData<UserInfoDTO?>()
        ChessDatabase.databaseWriteExecutor.execute {
            data.postValue(usersDAO.getUserInfo(userId))
        }
        return data as LiveData<T?>
    }

    override fun <T : UserInfo> deleteUser(user: T) {
        ChessDatabase.databaseWriteExecutor.execute {
            usersDAO.deleteUser(user as UserInfoDTO)
        }
    }

    override fun addSolvedProblem(userId: Int, solvedProblem: SolvedProblem) {
        ChessDatabase.databaseWriteExecutor.execute {
            try {
                usersDAO.registerSolved(SolvedProblemDTO(userId, solvedProblem))
            } catch (e: Exception) {
                Log.e("UsersRepo/addProblem", e.message, e)
            }
        }
    }
}
