package dev.ky3he4ik.chessproblems.presentation.repository.room

import android.app.Application
import androidx.lifecycle.LiveData
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.presentation.repository.ProblemsRepository
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemInfoDTO
import dev.ky3he4ik.chessproblems.presentation.repository.room.dao.ProblemsDAO

class ProblemsRepositoryImpl(application: Application) : ProblemsRepository {
    private var problemsDAO: ProblemsDAO
    private var allProblems: LiveData<List<ProblemInfoDTO>>

    init {
        val db: ChessDatabase = ChessDatabase.getInstance(application)
        problemsDAO = db.problemsDAO()
        allProblems = problemsDAO.getAllProblems()
    }

    override fun <T : ProblemInfo> getAllProblems(): LiveData<List<T>> {
        return allProblems as LiveData<List<T>>
    }

    override fun <T : ProblemInfo> addProblem(problem: T) {
        ChessDatabase.databaseWriteExecutor.execute {
            problemsDAO.addProblem(
                ProblemInfoDTO(problem)
            )
        }
    }

    override fun <T : ProblemInfo> getProblem(problemId: Int): T? {
        return problemsDAO.getProblem(problemId) as T?
    }

    override fun <T : ProblemInfo> deleteProblem(problem: T) {
        ChessDatabase.databaseWriteExecutor.execute { problemsDAO.deleteProblem(problem as ProblemInfoDTO) }
    }
}
