package dev.ky3he4ik.chessproblems.presentation.repository.model.problems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition

@Entity(
    tableName = "figure_position",
    foreignKeys = [ForeignKey(
        entity = ProblemDTO::class,
        parentColumns = arrayOf("problem_id"),
        childColumns = arrayOf("problem_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
class FigurePositionDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
//    @ColumnInfo(name = "problem_id")
    val problem_id: Int,
    override val letter: Char,
    override val number: Int,
    override val figure: Char?,
    @ColumnInfo(name = "is_white")
    override val isWhite: Boolean,
) : FigurePosition(letter, number, figure, isWhite) {
    constructor(problemId: Int, position: FigurePosition) : this(
        0,
        problemId,
        position.letter,
        position.number,
        position.figure,
        position.isWhite
    )
}
