package dev.ky3he4ik.chessproblems.presentation.repository.network.google

import dev.ky3he4ik.chessproblems.BuildConfig
import retrofit2.Call
import retrofit2.http.*

interface GoogleApi {
    @GET("oauth2/v2/userinfo")
    fun getUserInfo(
        @Header("Authorization") access_token: String,
    ): Call<GoogleAuth.APIResponse?>?

    @FormUrlEncoded
    @POST("token")
    fun getToken(@Field("code") code: String,
                 @Field("redirect_uri")  redirect_uri: String,
                 @Field("client_id")  client_id: String = BuildConfig.GOOGLE_AUTH_KEY,
//                 @Field("client_secret")  client_secret: String,
                 @Field("grant_type")  grant_type: String = "authorization_code"
    ): Call<GoogleAuth.TokenResponse>?
}