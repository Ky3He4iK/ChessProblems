package dev.ky3he4ik.chessproblems.domain.model.users

open class UserInfo(
    open val userId: Int,
    open val nick: String,
    open val image: String?,
    open val rating: Int,
    open val solved: Int,
    open val solvedProblems: List<SolvedProblem>,
    open val mail: String,
    open val roleLevel: Int,
) {
    enum class Roles(val roleLevel: Int) {
        USER(1), // 1
        PREMIUM(5), // 4+1
        MODERATOR(21), // 16+4+1
        ADMIN(255),
    }
}
