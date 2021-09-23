package dev.ky3he4ik.chessproblems.presentation.repository.model.problems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove

@Entity(tableName = "problem_info")
class ProblemInfoDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "problem_id")
    override val problemId: Int,
    @ColumnInfo(name = "title")
    override val title: String,
    @ColumnInfo(name = "description")
    override val description: String,
    @ColumnInfo(name = "difficulty")
    override val difficulty: Int,
    @ColumnInfo(name = "white_starts")
    override val whiteStarts: Boolean,
    @ColumnInfo(name = "moves")
    override val moves: List<ProblemMove>,
    @ColumnInfo(name = "figure_position")
    override val figurePosition: List<FigurePosition>,
) : ProblemInfo(
    problemId,
    title,
    description,
    difficulty,
    whiteStarts,
    moves,
    figurePosition,
) {
    constructor(problem: ProblemInfo) : this(
        problem.problemId,
        problem.title,
        problem.description,
        problem.difficulty,
        problem.whiteStarts,
        problem.moves,
        problem.figurePosition,
    )
}
