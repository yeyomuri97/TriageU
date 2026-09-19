package edu.unicauca.aplimovil.composelble4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import edu.unicauca.aplimovil.composelble4.ui.UnicaucaApp
import edu.unicauca.aplimovil.composelble4.ui.theme.Composelble4Theme
import edu.unicauca.aplimovil.composelble4.ui.theme.BackgroundGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Composelble4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundGreen
                ) {
                    UnicaucaApp()
                }
            }
        }
    }
}
