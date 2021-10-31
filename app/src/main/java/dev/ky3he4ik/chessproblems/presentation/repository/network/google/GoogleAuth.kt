package dev.ky3he4ik.chessproblems.presentation.repository.network.google

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

class GoogleAuth : OAuth2Provider() {
    private val api: GoogleApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(GoogleApi::class.java)
    }

    override val authUrl: String =
        "https://accounts.google.com/o/oauth2/v2/auth?client_id=${BuildConfig.GOOGLE_AUTH_KEY}&response_type=code&redirect_uri=dev.ky3he4ik.chessproblems%3A/oauth2redirect&scope=https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email"
    override val method: String
        get() = "Google"

    override fun getWebViewClient(
        owner: LifecycleOwner,
        navController: NavController
    ): WebViewClient {
        return object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (request.url.toString().contains("dev.ky3he4ik.chessproblems:/oauth2redirect")) {
                    val authCode =
                        Uri.parse(request.url.toString()).getQueryParameter("code") ?: return true
                    api.getToken(authCode, "dev.ky3he4ik.chessproblems%3A/oauth2token")
                        ?.enqueue(object :
                            Callback<TokenResponse?> {
                            override fun onResponse(
                                call: Call<TokenResponse?>,
                                response: Response<TokenResponse?>
                            ) {
                                val body = response.body() ?: return
                                if (body.access_token != null)
                                    onTokenResponse(body.access_token, body.token_type ?: "Bearer", navController)
                            }

                            override fun onFailure(call: Call<TokenResponse?>, t: Throwable) {
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

    private fun onTokenResponse(token: String, token_type: String, navController: NavController) {
        api.getUserInfo("$token_type $token")?.enqueue(object :
            Callback<APIResponse?> {
            override fun onResponse(
                call: Call<APIResponse?>,
                response: Response<APIResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val bundle = Bundle()
                    bundle.putString(
                        "mail",
                        response.body()?.email
                    )
                    bundle.putString(
                        "nick",
                        response.body()?.name
                    )
                    bundle.putInt(
                        "role_level",
                        UserInfo.Roles.ADMIN.roleLevel
                    )
                    navController.navigate(R.id.action_webFragment_to_addUser, bundle)
                }
            }

            override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
                Log.e("BlundersAPI", "Fail to get problem: " + t.message, t)
            }
        })
    }

    data class APIResponse(
        val picture: String? = null,
        val name: String? = null,
        val locale: String? = null,
        val email: String? = null,
        val given_name: String? = null,
        val id: String? = null,
        val verified_email: Boolean = false,
    )

    data class TokenResponse(
        val access_token: String? = null,
        val id_token: String? = null,
        val expires_in: String? = null,
        val token_type: String? = null,
        val scope: String? = null,
        val refresh_token: String? = null,
    )

    data class TokenRequest(
        val code: String,
        val redirect_uri: String,
        val cliend_id: String = BuildConfig.GOOGLE_AUTH_KEY,
        val client_secret: String,
        val grant_type: String = "authorization_code"
        //code=4%2F0AX4XfWgvvcWjr5trajZ2MHIjKKREj66SnIOM57W-4XIgK-FJyZ97UlHYiOLCRWgqiaEtqQ&redirect_uri=https%3A%2F%2Fdevelopers.google.com%2Foauthplayground&
        // client_id=407408718192.apps.googleusercontent.com&client_secret=************&scope=&grant_type=authorization_code
    )
}
