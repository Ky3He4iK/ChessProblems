package dev.ky3he4ik.chessproblems.presentation.repository.model.users

import androidx.room.*
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemDTO

@Entity(
    tableName = "solved_problem", foreignKeys = [ForeignKey(
        entity = UserDTO::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ProblemDTO::class,
        parentColumns = arrayOf("problem_id"),
        childColumns = arrayOf("problem_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
class SolvedProblemDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "problem_id")
    override val problemId: Int,
    @ColumnInfo(name = "solving_time")
    override val solvingTime: Long,
) : SolvedProblem(problemId, solvingTime) {
    constructor(userId: Int, problem: SolvedProblem) : this(
        0,
        userId,
        problem.problemId,
        problem.solvingTime
    )
}
