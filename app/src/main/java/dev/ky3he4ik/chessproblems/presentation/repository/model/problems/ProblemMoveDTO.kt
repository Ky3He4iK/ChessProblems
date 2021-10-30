package dev.ky3he4ik.chessproblems.presentation.repository.model.problems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove


@Entity(
    tableName = "problem_move",
    foreignKeys = [ForeignKey(
        entity = ProblemDTO::class,
        parentColumns = arrayOf("problem_id"),
        childColumns = arrayOf("problem_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
class ProblemMoveDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "problem_id")
    val problem_id: Int,
    @ColumnInfo(name = "letter_start")
    override val letterStart: Int,
    @ColumnInfo(name = "number_start")
    override val numberStart: Int,
    @ColumnInfo(name = "letter_end")
    override val letterEnd: Int,
    @ColumnInfo(name = "number_end")
    override val numberEnd: Int,
    override val promotion: Char?,
    @ColumnInfo(name = "is_castling")
    override val isCastling: Boolean,
    override val move: String
) : ProblemMove(letterStart, numberStart, letterEnd, numberEnd, promotion, isCastling, move) {
    constructor(problemId: Int, move: ProblemMove) : this(
        0,
        problemId,
        move.letterStart,
        move.numberStart,
        move.letterEnd,
        move.numberEnd,
        move.promotion,
        move.isCastling,
        move.move,
    )
}
