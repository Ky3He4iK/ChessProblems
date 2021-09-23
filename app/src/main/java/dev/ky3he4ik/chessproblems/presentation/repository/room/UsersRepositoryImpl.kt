package dev.ky3he4ik.chessproblems.presentation.repository.room

import android.app.Application
import androidx.lifecycle.LiveData
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.repository.UsersRepository
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserInfoDTO
import dev.ky3he4ik.chessproblems.presentation.repository.room.dao.UsersDAO

class UsersRepositoryImpl(application: Application) : UsersRepository {
    private var usersDAO: UsersDAO
    private var allUsers: LiveData<List<UserInfoDTO>>

    init {
        val db: ChessDatabase = ChessDatabase.getInstance(application)
        usersDAO = db.usersDAO()
        allUsers = usersDAO.getAllUsers()
    }

    override fun <T : UserInfo> getAllUsers(): LiveData<List<T>> {
        return allUsers as LiveData<List<T>>
    }

    override fun <T : UserInfo> addUser(user: T) {
        ChessDatabase.databaseWriteExecutor.execute {
            usersDAO.addUser(
                UserInfoDTO(user)
            )
        }
    }

    override fun <T : UserInfo> getUser(userId: Int): T? {
        return usersDAO.getUser(userId) as T?
    }

    override fun <T : UserInfo> deleteUser(user: T) {
        ChessDatabase.databaseWriteExecutor.execute { usersDAO.deleteUser(user as UserInfoDTO) }
    }
}
