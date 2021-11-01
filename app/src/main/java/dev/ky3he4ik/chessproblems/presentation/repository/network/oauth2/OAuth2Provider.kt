package dev.ky3he4ik.chessproblems.presentation.repository.network.oauth2

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController

abstract class OAuth2Provider {
    abstract val authUrl: String
    abstract val method: String

    fun auth(navigateToWeb: (Bundle) -> Unit) {
        val bundle = Bundle()
        bundle.putString("url", authUrl)
        bundle.putString("method", method)
        navigateToWeb(bundle)
    }

    abstract fun getWebViewClient(
        owner: LifecycleOwner,
        navController: NavController
    ): WebViewClient
}
