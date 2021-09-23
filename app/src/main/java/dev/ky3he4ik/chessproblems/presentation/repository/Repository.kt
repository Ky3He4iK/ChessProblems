package dev.ky3he4ik.chessproblems.presentation.repository

import android.app.Application
import dev.ky3he4ik.chessproblems.presentation.repository.mock.MockProblemsRepository
import dev.ky3he4ik.chessproblems.presentation.repository.mock.MockUsersRepository
import dev.ky3he4ik.chessproblems.presentation.repository.room.ProblemsRepositoryImpl
import dev.ky3he4ik.chessproblems.presentation.repository.room.UsersRepositoryImpl

class Repository {
    companion object {
        private var problemsRepository: ProblemsRepository? = null

        private var usersRepository: UsersRepository? = null

        fun getProblemsRepository(): ProblemsRepository {
            problemsRepository = problemsRepository ?: MockProblemsRepository()
            return problemsRepository!!
        }

        fun getUsersRepository(): UsersRepository {
            usersRepository= usersRepository ?: MockUsersRepository()
            return usersRepository!!
        }

        fun initRepository(application: Application) {
            problemsRepository = problemsRepository ?: ProblemsRepositoryImpl(application)

            usersRepository = usersRepository ?: UsersRepositoryImpl(application)
        }
    }
}
