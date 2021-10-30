package dev.ky3he4ik.chessproblems.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dev.ky3he4ik.chessproblems.MainActivity
import dev.ky3he4ik.chessproblems.databinding.WebFragmentBinding
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class WebFragment : Fragment() {
    private lateinit var binding: WebFragmentBinding
    private var url: String? = null
    private var method: String? = null
    private var urlContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val args = navArgs<WebFragmentArgs>().value
            url = args.url
            method = args.method
            urlContent = args.urlContent
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WebFragmentBinding.inflate(inflater, container, false)
        if (url != null && !url.isNullOrEmpty()) {
            CookieManager.getInstance().removeAllCookies(null)
            binding.web.clearCache(true)
            binding.web.loadUrl(url ?: "")
            val provider = Repository.OAUTH.values().find { it.name == method }?.provider
            if (provider != null)
                binding.web.webViewClient = provider.getWebViewClient(
                    viewLifecycleOwner,
                    (requireActivity() as MainActivity).navHost.navController
                )
        } else if (urlContent != null) {
            CookieManager.getInstance().removeAllCookies(null)
            binding.web.clearCache(true)
            binding.web.loadUrl("https://oauth.vk.com/authorize/$urlContent")
            binding.web.webViewClient = WebViewClient()
//            val provider = Repository.OAUTH.values().find { it.name == method }?.provider
//            if (provider != null)
//                binding?.web?.webViewClient = provider.onAuthResponse(
//                    viewLifecycleOwner,
//                    (requireActivity() as MainActivity).navHost.navController
//                )
        }
        return binding.root
    }
}
