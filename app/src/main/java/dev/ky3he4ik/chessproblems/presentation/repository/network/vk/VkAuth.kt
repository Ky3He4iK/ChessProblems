package dev.ky3he4ik.chessproblems.presentation.repository.network.vk

import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

import androidx.navigation.Navigation

import dev.ky3he4ik.chessproblems.MainActivity

class VkAuth {
    fun auth(activity: MainActivity) {
        val bundle = Bundle()
        bundle.putString(
            "url",
            "https://oauth.vk.com/authorize?client_id=7954421&scope=email&redirect_uri=https://oauth.vk.com/blank.html&display=mobile&response_type=token&scope=offline,email"
        )
        //
//        Navigation.findNavController(activity.binding.navHostFragment)
//            .navigate(R.id.action_authFragment_to_webFragment, bundle)
    }

    fun oath2VK(activity: MainActivity): WebViewClient {
        return object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (request.url.toString().contains("https://oauth.vk.com/blank.html#")) {
                    val parsedUri = Uri.parse(request.url.toString().replace("#", "?"))
                    val token = parsedUri.getQueryParameter("access_token")
                    val email = parsedUri.getQueryParameter("email")
//
//                    ServiceLocator.getInstance().getRepository().findPerson(email, activity)
//                        .observe(activity) { person ->
//                            if (person == null) {
//                                val newPerson = Person()
//                                newPerson.setEmail(email)
//                                ServiceLocator.getInstance().setPerson(newPerson)
//                            } else {
//                                ServiceLocator.getInstance().setPerson(person)
//                            }
//                        }
//                    ServiceLocator.getInstance().getVK_API().getPersonInfo(token, activity)
//
//                    //activity.getPreferences(Context.MODE_PRIVATE).edit().putString("token", token).putString("email", email).apply();
//                    Navigation.findNavController(activity.mBinding.navHostFragment)
//                        .navigate(R.id.action_webFragment_to_authFragment)
                    return false
                }
                view.loadUrl(request.url.toString())
                return true
            }
        }
    }
}
