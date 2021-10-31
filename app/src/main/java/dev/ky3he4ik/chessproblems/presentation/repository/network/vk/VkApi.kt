package dev.ky3he4ik.chessproblems.presentation.repository.network.vk

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface VkApi {
    @GET("account.getProfileInfo")
    fun getUserInfo(
        @Query("access_token") access_token: String,
        @QueryMap api_info: Map<String, String> = mapOf("v" to "5.131"),
    ): Call<VkAuth.APIResponse?>?
}
