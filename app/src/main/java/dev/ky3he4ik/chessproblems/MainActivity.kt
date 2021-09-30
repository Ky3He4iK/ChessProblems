package dev.ky3he4ik.chessproblems

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.ky3he4ik.chessproblems.presentation.repository.Repository

class MainActivity : AppCompatActivity() {
    private var navHost: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Repository.initRepository(application)
        navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navHost?.navController?.let {
            NavigationUI.setupWithNavController(
                findViewById<BottomNavigationView>(R.id.bottomNav),
                it
            )
            intent.data?.let { navHost?.navController?.navigate(it) }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navHost?.navController?.handleDeepLink(intent)
    }
}
