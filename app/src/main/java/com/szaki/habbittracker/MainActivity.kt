package com.szaki.habbittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import com.szaki.habbittracker.data.AppPreferences
import com.szaki.habbittracker.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val settings = AppPreferences.loadSettings(this)

        setContent {
            MaterialTheme(
                colorScheme = if (settings.darkMode) darkColorScheme() else lightColorScheme()
            ) {
                Box(
                    modifier = Modifier.scale(settings.fontScale)
                ) {
                    NavGraph()
                }
            }
        }
    }
}
