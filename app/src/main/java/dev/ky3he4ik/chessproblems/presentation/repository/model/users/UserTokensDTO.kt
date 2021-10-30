package dev.ky3he4ik.chessproblems.presentation.repository.model.users

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.ky3he4ik.chessproblems.domain.model.users.UserTokens

@Entity(
    tableName = "user_tokens", foreignKeys = [ForeignKey(
        entity = UserInfoDTO::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
class UserTokensDTO(
    @PrimaryKey
    val userId: Int,
    override val vk: String? = null,
    override val google: String? = null,
    override val yandex: String? = null,
) : UserTokens(vk, google, yandex) {
    constructor(tokens: UserTokens, userId: Int) : this(userId, tokens.vk, tokens.google, tokens.yandex)
}
