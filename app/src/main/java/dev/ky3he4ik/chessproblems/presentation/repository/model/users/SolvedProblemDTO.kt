package dev.ky3he4ik.chessproblems.presentation.repository.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemInfoDTO

@Entity(
    tableName = "user_tokens", foreignKeys = [ForeignKey(
        entity = UserInfoDTO::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ProblemInfoDTO::class,
        parentColumns = arrayOf("problem_id"),
        childColumns = arrayOf("problem_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
class SolvedProblemDTO(
    @PrimaryKey
    val userId: Int,
    @ColumnInfo(name = "problem_id")
    override val problemId: Int,
    @ColumnInfo(name = "solving_time")
    override val solvingTime: Long,
) : SolvedProblem(problemId, solvingTime) {
    constructor(userId: Int, problem: SolvedProblem) : this(
        userId,
        problem.problemId,
        problem.solvingTime
    )
}
