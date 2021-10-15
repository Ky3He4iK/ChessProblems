package dev.ky3he4ik.chessproblems.presentation.repository

import android.app.Application
import dev.ky3he4ik.chessproblems.presentation.repository.mock.MockProblemsRepository
import dev.ky3he4ik.chessproblems.presentation.repository.mock.MockUsersRepository
import dev.ky3he4ik.chessproblems.presentation.repository.room.ProblemsRepositoryImpl
import dev.ky3he4ik.chessproblems.presentation.repository.room.UsersRepositoryImpl

object Repository {
    var problemsRepository: ProblemsRepository? = null
        get() {
            if (field == null)
                field = MockProblemsRepository()
            return field
        }
        private set

    var usersRepository: UsersRepository? = null
        get() {
            if (field == null)
                field = MockUsersRepository()
            return field
        }

    fun initRepository(application: Application) {
        problemsRepository = ProblemsRepositoryImpl(application)
        usersRepository = UsersRepositoryImpl(application)
    }
}
