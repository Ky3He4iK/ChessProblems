package dev.ky3he4ik.chessproblems.presentation.repository.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.presentation.repository.ProblemsRepository
import dev.ky3he4ik.chessproblems.presentation.repository.model.problems.ProblemInfoDTO
import dev.ky3he4ik.chessproblems.presentation.repository.room.dao.ProblemsDAO

class ProblemsRepositoryImpl(application: Application) : ProblemsRepository {
    private var problemsDAO: ProblemsDAO

    init {
        val db: ChessDatabase = ChessDatabase.getInstance(application)
        problemsDAO = db.problemsDAO()
    }

    override fun <T : ProblemInfo> getAllProblems(): LiveData<List<T>> {
        val data = MutableLiveData<List<ProblemInfoDTO>>()
        ChessDatabase.databaseWriteExecutor.execute {
            data.postValue(problemsDAO.getAllProblems())
        }
        return data as LiveData<List<T>>
    }

    override fun <T : ProblemInfo> getUnsolvedProblemsLive(userId: Int?): LiveData<List<T>> {
        userId ?: return getAllProblemsLive()
        val data = MutableLiveData<List<ProblemInfoDTO>>()
        ChessDatabase.databaseWriteExecutor.execute {
            data.postValue(problemsDAO.getUnsolvedProblemsDTOs(userId).mapNotNull {
                problemsDAO.getProblemInfo(it.problemId)
            })
        }
        return data as LiveData<List<T>>
    }

    override fun <T : ProblemInfo> getSolvedProblemsLive(userId: Int?): LiveData<List<T>> {
        val data = MutableLiveData<List<ProblemInfoDTO>>()
        userId ?: return data as LiveData<List<T>>
        ChessDatabase.databaseWriteExecutor.execute {
            data.postValue(problemsDAO.getSolvedProblemsDTOs(userId).mapNotNull {
                problemsDAO.getProblemInfo(it.problemId)
            })
        }
        return data as LiveData<List<T>>
    }

    override fun <T : ProblemInfo> getAllProblemsLive(): LiveData<List<T>> {
        val data = MutableLiveData<List<ProblemInfoDTO>>()
        ChessDatabase.databaseWriteExecutor.execute {
            data.postValue(problemsDAO.getAllProblemDTOs().mapNotNull {
                problemsDAO.getProblemInfo(it.problemId)
            })
        }
        return data as LiveData<List<T>>
    }

    override fun <T : ProblemInfo> addProblem(problem: T): LiveData<Int> {
        val data = MutableLiveData<Int>()
        ChessDatabase.databaseWriteExecutor.execute {
            val id = problemsDAO.addProblem(
                ProblemInfoDTO(problem)
            )
            data.postValue(id)
        }
        return data
    }

    override fun <T : ProblemInfo> getProblem(problemId: Int): LiveData<T?> {
        val data = MutableLiveData<ProblemInfoDTO?>()
        ChessDatabase.databaseWriteExecutor.execute {
            data.postValue(problemsDAO.getProblemInfo(problemId))
        }
        return data as LiveData<T?>
    }

    override fun <T : ProblemInfo> deleteProblem(problem: T) {
        ChessDatabase.databaseWriteExecutor.execute { problemsDAO.deleteProblem(problem as ProblemInfoDTO) }
    }
}
