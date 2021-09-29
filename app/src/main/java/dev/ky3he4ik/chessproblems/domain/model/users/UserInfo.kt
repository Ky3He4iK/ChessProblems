package dev.ky3he4ik.chessproblems.domain.model.users

open class UserInfo(
    open val userId: Int,
    open val nick: String,
    open val image: String?,
    open val rating: Int = 0,
    open val solved: Int = 0,
    open val solvedProblems: List<SolvedProblem>,
) {
    constructor(): this(0, "", null, 0, 0, arrayListOf())
}
