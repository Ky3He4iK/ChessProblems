package dev.ky3he4ik.chessproblems.domain.model.problems

open class FigurePosition(
    open val letter: Char,
    open val number: Int,
    open val figure: Char?,
    open val isWhite: Boolean,
) {
    fun toInfoString(): String {
        if (figure == null)
            return "$letter$number"
        return "$figure$letter$number"
    }
}
