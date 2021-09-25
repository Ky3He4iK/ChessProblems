package dev.ky3he4ik.chessproblems.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemInfoDTO

@Dao
interface ProblemsDAO {
    @Insert
    fun addProblem(problem: ProblemInfoDTO?)

    @Delete
    fun deleteProblem(problem: ProblemInfoDTO?)

    @Query("SELECT * FROM problem_info")
    fun getAllProblems(): LiveData<List<ProblemInfoDTO>>

    @Query("SELECT * FROM problem_info WHERE problem_id = :problemId")
    fun getProblem(problemId: Int): ProblemInfoDTO?

}
