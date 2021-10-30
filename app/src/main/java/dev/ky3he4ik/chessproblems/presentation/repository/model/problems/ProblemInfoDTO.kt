package dev.ky3he4ik.chessproblems.presentation.repository.model.problems

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo

class ProblemInfoDTO(
    @Embedded
    val problemDTO: ProblemDTO,

    @Relation(parentColumn = "problem_id", entityColumn = "problem_id")
    override val moves: List<ProblemMoveDTO>,

    @Relation(parentColumn = "problem_id", entityColumn = "problem_id")
    override val figurePosition: List<FigurePositionDTO>,
) : ProblemInfo(
    problemDTO.problemId,
    problemDTO.title,
    problemDTO.description,
    problemDTO.difficulty,
    problemDTO.whiteStarts,
    ArrayList(moves),
    ArrayList(figurePosition),
) {
    constructor(problem: ProblemInfo) : this(
        ProblemDTO(
            problem.problemId,
            problem.title,
            problem.description,
            problem.difficulty,
            problem.whiteStarts
        ),
        MutableList(problem.moves.size) { ProblemMoveDTO(problem.problemId, problem.moves[it]) },
        MutableList(problem.figurePosition.size) { FigurePositionDTO(problem.problemId, problem.figurePosition[it]) },
    )

    @Ignore
    override val problemId: Int = problemDTO.problemId

    @Ignore
    override val title: String = problemDTO.title

    @Ignore
    override val description: String = problemDTO.description

    @Ignore
    override val difficulty: Int = problemDTO.difficulty

    @Ignore
    override val whiteStarts: Boolean = problemDTO.whiteStarts
}
