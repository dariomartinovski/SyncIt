package mk.ukim.finki.syncit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mk.ukim.finki.syncit.navigation.AppNavigation
import mk.ukim.finki.syncit.utils.EnvConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EnvConfig.init(this)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}