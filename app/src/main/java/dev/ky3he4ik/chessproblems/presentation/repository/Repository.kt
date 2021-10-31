package dev.ky3he4ik.chessproblems.presentation.repository

import android.app.Application
import dev.ky3he4ik.chessproblems.presentation.repository.network.chessblunders.ChessBlunders
import dev.ky3he4ik.chessproblems.presentation.repository.network.google.GoogleAuth
import dev.ky3he4ik.chessproblems.presentation.repository.network.oauth2.OAuth2Provider
import dev.ky3he4ik.chessproblems.presentation.repository.network.vk.VkAuth
import dev.ky3he4ik.chessproblems.presentation.repository.room.ProblemsRepositoryImpl
import dev.ky3he4ik.chessproblems.presentation.repository.room.UsersRepositoryImpl

object Repository {
    private var _problemsRepository: ProblemsRepository? = null
    val problemsRepository: ProblemsRepository
        get() {
            return _problemsRepository ?: throw RuntimeException("Repository is not initiated!")
        }

    private var _usersRepository: UsersRepository? = null
    val usersRepository: UsersRepository
        get() {
            return _usersRepository ?: throw RuntimeException("Repository is not initiated!")
        }

    private var _chessBlunders: ChessBlunders? = null
    val chessBlunders: ChessBlunders
        get() {
            if (_chessBlunders == null)
                _chessBlunders = ChessBlunders()
            return _chessBlunders!!
        }

    var activeUserId: Int? = null

    fun initRepository(application: Application) {
        _problemsRepository = _problemsRepository ?: ProblemsRepositoryImpl(application)
        _usersRepository = _usersRepository ?: UsersRepositoryImpl(application)
    }

    enum class OAUTH(val provider: OAuth2Provider) {
        Vk(VkAuth()),
        Google(GoogleAuth()),
    }
}
