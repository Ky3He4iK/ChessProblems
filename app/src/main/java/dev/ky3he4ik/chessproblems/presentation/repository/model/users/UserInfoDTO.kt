package dev.ky3he4ik.chessproblems.presentation.repository.model.users

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo

class UserInfoDTO(
    @Embedded
    val userDTO: UserDTO,

    @Relation(parentColumn = "user_id", entityColumn = "user_id")
    override val solvedProblems: List<SolvedProblemDTO>,
) : UserInfo(
    userDTO.userId,
    userDTO.nick,
    userDTO.image,
    userDTO.rating,
    userDTO.solved,
    solvedProblems,
    userDTO.mail,
    userDTO.roleLevel,
) {
    constructor(user: UserInfo) : this(
        UserDTO(
            user.userId,
            user.nick,
            user.image,
            user.rating,
            user.solved,
            user.mail,
            user.roleLevel,
        ),
        user.solvedProblems.map { SolvedProblemDTO(user.userId, it) },
    )

    @Ignore
    override val userId: Int = userDTO.userId

    @Ignore
    override val nick: String = userDTO.nick

    @Ignore
    override val image: String? = userDTO.image

    @Ignore
    override val rating: Int = userDTO.rating

    @Ignore
    override val solved: Int = userDTO.solved

    @Ignore
    override val mail: String = userDTO.mail

    @Ignore
    override val roleLevel: Int = userDTO.roleLevel
}
