package dev.ky3he4ik.chessproblems.presentation.repository.model.problems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove

@Entity(tableName = "problem_info")
class ProblemInfoDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "problem_id")
    override var problemId: Int,
    override var title: String,
    override var image: String?,
    override var description: String,
    override var difficulty: Int,

    @ColumnInfo(name = "white_starts")
    override var whiteStarts: Boolean,

    @Relation(parentColumn = "problem_id", entityColumn = "problem_id")
    override var moves: List<ProblemMove>,

    @ColumnInfo(name = "figure_position")
    @Relation(parentColumn = "problem_id", entityColumn = "problem_id")
    override var figurePosition: List<FigurePosition>,
) : ProblemInfo(
    problemId,
    title,
    image,
    description,
    difficulty,
    whiteStarts,
    moves,
    figurePosition,
) {
    constructor(problem: ProblemInfo) : this(
        problem.problemId,
        problem.title,
        problem.image,
        problem.description,
        problem.difficulty,
        problem.whiteStarts,
        problem.moves,
        problem.figurePosition,
    )
}
