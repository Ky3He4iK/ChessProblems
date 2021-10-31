package dev.ky3he4ik.chessproblems.domain.model.problems

open class ProblemMove(
    open val letterStart: Int,
    open val numberStart: Int,
    open val letterEnd: Int,
    open val numberEnd: Int,
    open val promotion: Char?,
    open val isCastling: Boolean,
    open val move: String
) {
    override fun toString(): String = move
}
