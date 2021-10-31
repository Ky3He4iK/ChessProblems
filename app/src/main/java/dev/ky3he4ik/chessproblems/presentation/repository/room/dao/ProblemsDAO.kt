package dev.ky3he4ik.chessproblems.presentation.repository.room.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.FigurePositionDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemInfoDTO
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemMoveDTO

@Dao
abstract class ProblemsDAO {
    @Transaction
    open fun addProblem(problem: ProblemInfoDTO): Int {
        addProblem(problem.problemDTO)
        val problemId = getLastInserted()
        problem.moves.forEach {
            Log.d("ProblemsDAO/moves", it.toString())
            addProblemMove(ProblemMoveDTO(problemId, it))
        }
        problem.figurePosition.forEach {
            Log.d("ProblemsDAO/position", it.toString())
            addProblemPosition(FigurePositionDTO(problemId, it))
        }
        return problemId
    }

    @Transaction
    open fun deleteProblem(problem: ProblemInfoDTO) {
        problem.moves.forEach { deleteProblemMove(it) }
        problem.figurePosition.forEach { deleteProblemPosition(it) }
        deleteProblem(problem.problemDTO)
    }

    @Query("SELECT last_insert_rowid()")
    abstract fun getLastInserted(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addProblem(problem: ProblemDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addProblemPosition(positionDTO: FigurePositionDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addProblemMove(moveDTO: ProblemMoveDTO)

    @Delete
    abstract fun deleteProblem(problem: ProblemDTO)

    @Delete
    abstract fun deleteProblemPosition(positionDTO: FigurePositionDTO)

    @Delete
    abstract fun deleteProblemMove(moveDTO: ProblemMoveDTO)

    @Query("SELECT * FROM problem_info WHERE problem_id = :problemId")
    abstract fun getProblem(problemId: Int): ProblemDTO?

    @Query("SELECT * FROM figure_position WHERE problem_id = :problemId")
    abstract fun getProblemPosition(problemId: Int): List<FigurePositionDTO>

    @Query("SELECT * FROM problem_move WHERE problem_id = :problemId")
    abstract fun getProblemMove(problemId: Int): List<ProblemMoveDTO>

    @Query("SELECT * FROM problem_info")
    abstract fun getAllProblemDTOs(): List<ProblemDTO>

    @Query("SELECT * FROM problem_info")
    abstract fun getAllProblemDTOsLive(): LiveData<List<ProblemDTO>>

    @Transaction
    open fun getAllProblems(): List<ProblemInfoDTO> {
        return getAllProblemDTOs().mapNotNull { getProblemInfo(it.problemId) }
    }

    @Transaction
    open fun getProblemInfo(problemId: Int): ProblemInfoDTO? {
        val problem = getProblem(problemId) ?: return null
        val problemMoves = getProblemMove(problemId)
        val problemPositions = getProblemPosition(problemId)
        return ProblemInfoDTO(problem, problemMoves, problemPositions)
    }
}
