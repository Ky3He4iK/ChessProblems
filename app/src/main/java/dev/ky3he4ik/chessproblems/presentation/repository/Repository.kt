package dev.ky3he4ik.chessproblems.presentation.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dev.ky3he4ik.chessproblems.presentation.repository.network.chessblunders.ChessBlunders
import dev.ky3he4ik.chessproblems.presentation.repository.network.oauth2.OAuth2Provider
import dev.ky3he4ik.chessproblems.presentation.repository.network.vk.VkAuth
import dev.ky3he4ik.chessproblems.presentation.repository.room.ProblemsRepositoryImpl
import dev.ky3he4ik.chessproblems.presentation.repository.room.UsersRepositoryImpl

object Repository {
    private const val PREFS_NAME = "kychess_shared"
    private const val PREF_ACTIVE_USER = "active_user"

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
        set(value)
        {
            field = value
            field?.let {
                prefs?.edit(true) { putInt(PREF_ACTIVE_USER, it) }
            }
        }

    private var prefs: SharedPreferences? = null

    fun initRepository(application: Application) {
        _problemsRepository = _problemsRepository ?: ProblemsRepositoryImpl(application)
        _usersRepository = _usersRepository ?: UsersRepositoryImpl(application)
        prefs = prefs ?: application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userId = prefs?.getInt(PREF_ACTIVE_USER, 0) ?: -1
        if (usersRepository.hasUser(userId))
            activeUserId = userId
    }

    enum class OAUTH(val provider: OAuth2Provider) {
        Vk(VkAuth()),
    }
}
