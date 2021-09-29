package dev.ky3he4ik.chessproblems.domain.model.problems

open class ProblemMove(
    open val posStart: String,
    open val posEnd: String,
) {
    constructor(): this("", "")
}
