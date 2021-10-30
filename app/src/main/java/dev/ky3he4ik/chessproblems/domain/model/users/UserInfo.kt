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
    open val tokens: UserTokens?,
) {
    enum class Roles(val roleLevel: Int) {
        USER(2),
        PREMIUM(5),
        MODERATOR(20),
        ADMIN(50),
    }
}
