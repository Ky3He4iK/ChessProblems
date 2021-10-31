package dev.ky3he4ik.chessproblems.domain.operations

import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo

object UserOperations {
    fun canAddProblems(userInfo: UserInfo): Boolean =
        userInfo.roleLevel >= UserInfo.Roles.PREMIUM.roleLevel

    fun canDeleteUsers(userInfo: UserInfo): Boolean =
        userInfo.roleLevel >= UserInfo.Roles.MODERATOR.roleLevel
}
