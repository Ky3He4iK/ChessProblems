package dev.ky3he4ik.chessproblems.presentation.repository

import androidx.lifecycle.LiveData
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo

interface ProblemsRepository {
    fun <T : ProblemInfo> getAllProblems(): LiveData<List<T>>
    fun <T : ProblemInfo> getUnsolvedProblemsLive(userId: Int?): LiveData<List<T>>
    fun <T : ProblemInfo> getSolvedProblemsLive(userId: Int?): LiveData<List<T>>
    fun <T : ProblemInfo> getAllProblemsLive(): LiveData<List<T>>
    fun <T : ProblemInfo> addProblem(problem: T): LiveData<Int>
    fun <T : ProblemInfo> getProblem(problemId: Int): LiveData<T?>
    fun <T : ProblemInfo> deleteProblem(problem: T)
}
