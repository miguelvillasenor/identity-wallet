package dev.mvillasenor.web5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mvillasenor.web5.did.DidsRoutes
import dev.mvillasenor.web5.did.didNavGraph
import dev.mvillasenor.web5.ui.theme.Web5WalletTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Web5WalletTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = DidsRoutes.DID_GRAPH_ROUTE
                ) {
                    didNavGraph(navController)
                }
            }
        }
    }
}
