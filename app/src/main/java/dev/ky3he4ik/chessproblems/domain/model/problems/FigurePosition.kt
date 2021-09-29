package dev.ky3he4ik.chessproblems.domain.model.problems

open class FigurePosition(
    open val isWhite: Boolean,
    open val code: String,
) {
    constructor(): this(false, "")
}
