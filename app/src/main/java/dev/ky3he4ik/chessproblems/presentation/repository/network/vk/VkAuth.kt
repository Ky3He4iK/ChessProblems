package dev.ky3he4ik.chessproblems.presentation.repository.network.vk

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import dev.ky3he4ik.chessproblems.BuildConfig
import dev.ky3he4ik.chessproblems.R
import dev.ky3he4ik.chessproblems.domain.model.users.UserInfo
import dev.ky3he4ik.chessproblems.presentation.repository.network.oauth2.OAuth2Provider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VkAuth : OAuth2Provider() {
    private val api: VkApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(VkApi::class.java)
    }

    override fun getWebViewClient(
        owner: LifecycleOwner,
        navController: NavController
    ): WebViewClient {
        return object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (request.url.toString().contains("https://oauth.vk.com/blank.html#")) {
                    val parsedUri = Uri.parse(request.url.toString().replace("#", "?"))
                    val token = parsedUri.getQueryParameter("access_token") ?: return true
                    val email = parsedUri.getQueryParameter("email") ?: ""

                    api.getUserInfo(token)?.enqueue(object :
                        Callback<APIResponse?> {
                        override fun onResponse(
                            call: Call<APIResponse?>,
                            response: Response<APIResponse?>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                val bundle = Bundle()

                                bundle.putString(
                                    "mail",
                                    email
                                )
                                bundle.putString(
                                    "nick",
                                    response.body()?.response?.first_name?.plus(" ")
                                        ?.plus(response.body()?.response?.last_name) ?: ""
                                )
                                bundle.putInt(
                                    "role_level",
                                    UserInfo.Roles.PREMIUM.roleLevel
                                )
                                navController.navigate(R.id.action_webFragment_to_addUser, bundle)
                            }
                        }

                        override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
                            Log.e("BlundersAPI", "Fail to get problem: " + t.message, t)
                        }
                    })
                    return false
                }
                view.loadUrl(request.url.toString())
                return true
            }
        }
    }

    override val authUrl: String =
        "https://oauth.vk.com/authorize?client_id=${BuildConfig.VK_AUTH_KEY}&scope=email&redirect_uri=https://oauth.vk.com/blank.html&display=mobile&response_type=token&scope=email"

    override val method: String = "Vk"

    data class APIResponse(val response: APIPerson? = null) {
        data class APIPerson (
            val first_name: String? = null,
            val last_name: String? = null,
            val id: Int = 0,
        )
    }
}
