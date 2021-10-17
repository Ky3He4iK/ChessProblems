package dev.ky3he4ik.chessproblems.domain.model.problems

open class ProblemInfo(
    open var problemId: Int,
    open var title: String,
    open var image: String?,
    open var description: String,
    open var difficulty: Int,
    open var whiteStarts: Boolean,
    open var moves: List<String>,
    open var figurePosition: List<FigurePosition>
)
