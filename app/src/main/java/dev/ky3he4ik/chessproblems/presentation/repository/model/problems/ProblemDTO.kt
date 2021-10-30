package dev.ky3he4ik.chessproblems.presentation.repository.model.problems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "problem_info")
class ProblemDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "problem_id")
    val problemId: Int,
    val title: String,
    val description: String,
    val difficulty: Int,

    @ColumnInfo(name = "white_starts")
    val whiteStarts: Boolean,
)