package dev.ky3he4ik.chessproblems.presentation.repository.network.chessblunders

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChessBlunders {
    private val api: ChessBlundersApi

    fun getRandomProblem(): LiveData<ProblemInfo?> {
        val info = MutableLiveData<ProblemInfo?>()
        api.getProblem(BlunderRequest())?.enqueue(object : Callback<ChessBlunderResponse?> {
            override fun onResponse(
                call: Call<ChessBlunderResponse?>,
                response: Response<ChessBlunderResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    info.value = response.body()?.data?.toProblemInfo()
                }
            }

            override fun onFailure(call: Call<ChessBlunderResponse?>, t: Throwable) {
                Log.e("BlundersAPI", "Fail to get problem: " + t.message, t)
            }
        })
        return info
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://chessblunders.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(ChessBlundersApi::class.java)
    }

    class BlunderRequest(val type: String = "explore") // alternative value: "rated"

    class ChessBlunderResponse(val data: ChessBlunderData, val status: String)
}
