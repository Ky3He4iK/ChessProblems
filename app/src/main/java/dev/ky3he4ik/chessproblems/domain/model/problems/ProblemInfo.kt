package dev.ky3he4ik.chessproblems.domain.model.problems

open class ProblemInfo(
    open val problemId: Int,
    open val title: String,
    open val description: String,
    open val difficulty: Int,
    open val whiteStarts: Boolean,
    open val moves: List<ProblemMove>,
    open val figurePosition: List<FigurePosition>
)
