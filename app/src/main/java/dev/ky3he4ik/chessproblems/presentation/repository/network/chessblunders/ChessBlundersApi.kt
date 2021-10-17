package dev.ky3he4ik.chessproblems.presentation.repository.network.chessblunders

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChessBlundersApi {
    @POST("api/blunder/get")
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun getProblem(@Body request: ChessBlunders.BlunderRequest): Call<ChessBlunders.ChessBlunderResponse?>?
}
