package com.example.mente_libre_app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity      // ✔ ESTA ES LA CORRECTA
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.example.mente_libre_app.navigation.AppNavGraph
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Mente_Libre_AppTheme(selectedTheme = null) {
                val navController = rememberAnimatedNavController()
                AppNavGraph(navController = navController, activity = this)
            }
        }
    }
}
