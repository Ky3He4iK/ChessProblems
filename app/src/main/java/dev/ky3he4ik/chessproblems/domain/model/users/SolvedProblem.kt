package dev.ky3he4ik.chessproblems.domain.model.users

open class SolvedProblem(
    open val problem_id: Int,
    open val solvingTime: Long,
) {
    constructor(): this(0, 0)
}
