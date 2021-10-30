package dev.ky3he4ik.chessproblems.presentation.repository.network.vk

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import dev.ky3he4ik.chessproblems.MainActivity
import dev.ky3he4ik.chessproblems.domain.model.problems.ProblemInfo
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.domain.model.users.UserTokens
import dev.ky3he4ik.chessproblems.presentation.repository.network.chessblunders.ChessBlunderData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Vk {
    companion object {
        val api_info = mapOf(Pair("v", "5.131"))
    }

    private val api: VkApi

    fun getPersonInfo(token: String, activity: MainActivity) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: VkApi = retrofit.create(VkApi::class.java)
//        api.getUserInfo(api_info, token)?.enqueue(object : Callback<APIResponse?> {
//            override fun onResponse(call: Call<APIResponse?>, response: Response<APIResponse?>) {
//                if (response.isSuccessful) {
//                    val person = UserInfo(0, "", null, 0, 0, listOf(), "", 0, null)
//
//                    if (person != null) {
//                        val name = (response.body()?.response?.first_name?.plus(" ") ?: "") +
//                                (response.body()?.response?.last_name ?: "")
//                        val tokens = UserTokens(token, null, null)
//
//                        ServiceLocator.getInstance().getRepository().findPerson(
//                            person.getEmail(),
//                            activity
//                        ).observe(activity) { person ->
//                            if (person == null) {
//                                ServiceLocator.getInstance().getRepository()
//                                    .addPerson(person)
//                            } else {
//                                if (!person.getFirst_name().equals(
//                                        person.getFirst_name()
//                                    ) ||
//                                    !person.getLast_name().equals(
//                                        person.getFirst_name()
//                                    ) ||
//                                    !person.getConnections().equals(
//                                        person
//                                            .getConnections()
//                                    ) ||
//                                    !person.getPhone().equals(
//                                        person.getPhone()
//                                    ) || person.getRole() !== ServiceLocator.getInstance()
//                                        .getPerson().getRole()
//                                ) {
//                                    person.setFirst_name(
//                                        person.getFirst_name()
//                                    )
//                                    person.setLast_name(
//                                        person.getLast_name()
//                                    )
//                                    person.setPhone(
//                                        person.getPhone()
//                                    )
//                                    person.setRole(Person.Role.User)
//                                    person.getConnections().put(
//                                        "vk",
//                                        person.getConnections()
//                                            .get("vk")
//                                    )
//                                    ServiceLocator.getInstance().getRepository()
//                                        .updatePerson(person)
//                                }
//                            }
//                            Navigation.findNavController(activity.mBinding.navHostFragment)
//                                .navigate(R.id.action_authFragment_to_partyList)
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
//                Log.e("VkApi", "Fail to get user: " + t.message, t)
//            }
//        })
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://chessblunders.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(VkApi::class.java)
    }

    class APIResponse {
        inner class APIPerson {
            var first_name: String? = null
            var last_name: String? = null
            var id = 0
        }

        var response: APIPerson? = null
    }
}
