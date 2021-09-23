package dev.ky3he4ik.chessproblems.presentation.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ky3he4ik.chessproblems.domain.model.problems.FigurePosition
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemMove
import dev.ky3he4ik.chessproblems.domain.model.users.SolvedProblem
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.repository.UsersRepository

class MockUsersRepository: UsersRepository {
    private var data: MutableLiveData<List<UserInfo>>
    private var list: MutableList<UserInfo>

    init {
        list = arrayListOf(
            UserInfo(
                1,
                "Beginner",
                42,
                0,
                arrayListOf(
                    SolvedProblem(
                        1,
                        42,
                    )
                )
            )
        )

        data = MutableLiveData(list)
    }

    override fun <T : UserInfo> getAllUsers(): LiveData<List<T>> {
        return data as LiveData<List<T>>
    }

    override fun <T : UserInfo> addUser(user: T) {
        list += user
    }

    override fun <T : UserInfo> getUser(userId: Int): T? {
        return list.find { it.userId == userId } as T?
    }

    override fun <T : UserInfo> deleteUser(user: T) {
        list -= user
    }
}