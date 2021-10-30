package dev.ky3he4ik.chessproblems.presentation.repository.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
class UserDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int,
    val nick: String,
    val image: String?,
    val rating: Int = 0,
    val solved: Int = 0,
    val mail: String,

    @ColumnInfo(name = "roleLevel")
    val roleLevel: Int,
)
