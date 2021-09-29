package dev.ky3he4ik.chessproblems.presentation.repository

import android.app.Application
import dev.ky3he4ik.chessproblems.presentation.repository.mock.MockProblemsRepository
import dev.ky3he4ik.chessproblems.presentation.repository.mock.MockUsersRepository
import dev.ky3he4ik.chessproblems.presentation.repository.room.ProblemsRepositoryImpl
import dev.ky3he4ik.chessproblems.presentation.repository.room.UsersRepositoryImpl

object Repository {
    private var _problemsRepository: ProblemsRepository? = null
    val problemsRepository: ProblemsRepository
        get() {
            if (_problemsRepository == null)
                _problemsRepository = MockProblemsRepository()
            return _problemsRepository!!
        }

    private var _usersRepository: UsersRepository? = null
    val usersRepository: UsersRepository
        get() {
            if (_usersRepository == null)
                _usersRepository = MockUsersRepository()
            return _usersRepository!!
        }

    fun initRepository(application: Application) {
        _problemsRepository = _problemsRepository ?: ProblemsRepositoryImpl(application)
        _usersRepository = _usersRepository ?: UsersRepositoryImpl(application)
    }
}
