package dev.ky3he4ik.chessproblems.presentation.repository.model.users

import androidx.room.*
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.domain.model.users.UserTokens

@Entity(tableName = "user_info")
class UserInfoDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    override val userId: Int,
    override val nick: String,
    override val image: String?,
    override val rating: Int = 0,
    override val solved: Int = 0,

    @ColumnInfo(name = "solved_problems")
    @Relation(parentColumn = "user_id", entityColumn = "user_id")
    override val solvedProblems: List<SolvedProblem>,
    override val mail: String,

    @ColumnInfo(name = "roleLevel")
    override val roleLevel: Int,

    @Relation(parentColumn = "user_id", entityColumn = "user_id")
    override val tokens: UserTokens?,
) : UserInfo(
    userId,
    nick,
    image,
    rating,
    solved,
    solvedProblems,
    mail,
    roleLevel,
    tokens,
) {
    constructor(user: UserInfo): this(
        user.userId,
        user.nick,
        user.image,
        user.rating,
        user.solved,
        user.solvedProblems,
        user.mail,
        user.roleLevel,
        user.tokens,
    )
}
