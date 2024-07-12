package com.example.fics_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.fics_compose.presentation.ui.screens.MainScreen
import com.example.fics_compose.presentation.ui.theme.FICSComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            FICSComposeTheme {
                MainScreen()
            }
        }
    }
}

