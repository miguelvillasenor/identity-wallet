package dev.mvillasenor.web5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.mvillasenor.web5.did.DidScreen
import dev.mvillasenor.web5.ui.theme.Web5WalletTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Web5WalletTheme {
                DidScreen()
            }
        }
    }
}
