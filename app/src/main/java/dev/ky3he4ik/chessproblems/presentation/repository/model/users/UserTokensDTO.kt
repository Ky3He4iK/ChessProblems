package dev.ky3he4ik.chessproblems.presentation.repository.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.ky3he4ik.chessproblems.domain.model.users.UserTokens

@Entity(
    tableName = "user_tokens", foreignKeys = [ForeignKey(
        entity = UserDTO::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
class UserTokensDTO(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userId: Int,
    override var vk: String? = null,
    override var google: String? = null,
    override var yandex: String? = null,
) : UserTokens(vk, google, yandex) {
    constructor(userId: Int, tokens: UserTokens) : this(userId, tokens.vk, tokens.google, tokens.yandex)
}
