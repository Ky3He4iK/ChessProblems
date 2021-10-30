package dev.ky3he4ik.chessproblems.presentation.repository.network.vk

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface VkApi {
    @GET("account.getProfileInfo")
    fun getUserInfo(
        @QueryMap api_info: Map<String, String>,
        @Query("access_token") access_token: String
    ): Call<Vk.APIResponse?>?
}
