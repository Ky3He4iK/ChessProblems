package dev.ky3he4ik.chessproblems.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.ky3he4ik.chessproblems.presentation.repository.model.users.UserInfoDTO

@Dao
interface UsersDAO {
    @Insert
    fun addUser(user: UserInfoDTO?)

    @Delete
    fun deleteUser(pattern: UserInfoDTO?)

    @Query("SELECT * FROM user_info")
    fun getAllUsers(): LiveData<List<UserInfoDTO>>

    @Query("SELECT * FROM user_info WHERE user_id = :userId")
    fun getUser(userId: Int): UserInfoDTO?
}