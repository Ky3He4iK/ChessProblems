package dev.ky3he4ik.chessproblems.presentation.repository.room.dao

import androidx.room.*
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.SolvedProblemDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserInfoDTO

@Dao
abstract class UsersDAO {
    @Transaction
    open fun addUser(user: UserInfoDTO) {
        addUser(user.userDTO)
        val userId = getLastInserted()
        user.solvedProblems.forEach { registerSolved(SolvedProblemDTO(userId, it)) }
    }

    @Transaction
    open fun deleteUser(user: UserInfoDTO) {
        user.solvedProblems.forEach { deleteUserSolved(it) }
        deleteUser(user.userDTO)
    }

    @Transaction
    open fun registerSolved(solved: SolvedProblemDTO) {
        addUserSolved(solved)
        increaseUserSolved(solved.userId)
    }

    @Query("SELECT last_insert_rowid()")
    abstract fun getLastInserted(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addUser(user: UserDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addUserSolved(solved: SolvedProblemDTO)

    @Query("update user_info set solved = solved + 1 where user_id = :userId")
    abstract fun increaseUserSolved(userId: Int)

    @Delete
    abstract fun deleteUser(user: UserDTO)

    @Delete
    abstract fun deleteUserSolved(solved: SolvedProblemDTO)

    @Query("SELECT * FROM user_info WHERE user_id = :userId")
    abstract fun getUser(userId: Int): UserDTO?

    @Query("SELECT * FROM solved_problem WHERE user_id = :userId")
    abstract fun getUserSolved(userId: Int): List<SolvedProblemDTO>

    @Query("SELECT * FROM user_info")
    abstract fun getAllUsersDTOs(): List<UserDTO>

    @Transaction
    open fun getAllUsers(): List<UserInfoDTO> {
        return getAllUsersDTOs().mapNotNull { getUserInfo(it.userId) }
    }

    @Transaction
    open fun getUserInfo(userId: Int): UserInfoDTO? {
        val user = getUser(userId) ?: return null
        val solved = getUserSolved(userId)
        return UserInfoDTO(user, solved)
    }
}
