package dev.ky3he4ik.chessproblems.presentation.repository.room.dao

import androidx.room.*
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.SolvedProblemDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserInfoDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserTokensDTO

@Dao
abstract class UsersDAO {
    @Transaction
    open fun addUser(user: UserInfoDTO) {
        addUser(user.userDTO)
        val userId = getLastInserted()
        if (user.tokens != null)
            addUserTokens(UserTokensDTO(userId, user.tokens))
        user.solvedProblems.forEach { addUserSolved(SolvedProblemDTO(userId, it)) }
    }

    @Transaction
    open fun deleteUser(user: UserInfoDTO) {
        if (user.tokens != null)
            deleteUserTokens(user.tokens)
        user.solvedProblems.forEach { deleteUserSolved(it) }
        deleteUser(user.userDTO)
    }

    @Query("SELECT last_insert_rowid()")
    abstract fun getLastInserted(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addUser(user: UserDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addUserTokens(token: UserTokensDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addUserSolved(solved: SolvedProblemDTO)

    @Delete
    abstract fun deleteUser(user: UserDTO)

    @Delete
    abstract fun deleteUserTokens(token: UserTokensDTO)

    @Delete
    abstract fun deleteUserSolved(solved: SolvedProblemDTO)

    @Query("SELECT * FROM user_info WHERE user_id = :userId")
    abstract fun getUser(userId: Int): UserDTO?

    @Query("SELECT * FROM user_tokens WHERE user_id = :userId")
    abstract fun getUserTokens(userId: Int): UserTokensDTO?

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
        val tokens = getUserTokens(userId)
        val solved = getUserSolved(userId)
        return UserInfoDTO(user, solved, tokens)
    }
}
