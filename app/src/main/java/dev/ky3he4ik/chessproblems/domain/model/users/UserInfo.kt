package dev.ky3he4ik.chessproblems.domain.model.users

open class UserInfo(
    open val userId: Int,
    open val nick: String,
    open val image: String?,
    open val rating: Int,
    open val solved: Int,
    open val solvedProblems: List<SolvedProblem>,
)
