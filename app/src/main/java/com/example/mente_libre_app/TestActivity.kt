package com.example.mente_libre_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.example.mente_libre_app.ui.screen.GeneroScreen
import com.example.mente_libre_app.ui.screen.HuellaScreen
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme

class TestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mente_Libre_AppTheme {
                GeneroScreen(
                    onNext = {}
                )
            }
        }
    }
}