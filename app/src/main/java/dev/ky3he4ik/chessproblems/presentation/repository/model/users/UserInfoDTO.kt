package dev.ky3he4ik.chessproblems.presentation.repository.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo

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
    override val solvedProblems: List<SolvedProblem>,
) : UserInfo(
    userId,
    nick,
    image,
    rating,
    solved,
    solvedProblems,
) {
    constructor(user: UserInfo): this(
        user.userId,
        user.nick,
        user.image,
        user.rating,
        user.solved,
        user.solvedProblems,
    )
}
