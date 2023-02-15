package dev.mvillasenor.web5.did

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.mvillasenor.web5.did.details.DidDetailsScreen

fun NavGraphBuilder.didNavGraph(navController: NavController) {
    navigation(route = DidsRoutes.DID_GRAPH_ROUTE, startDestination = DidsRoutes.DID_LIST_ROUTE) {
        composable(DidsRoutes.DID_LIST_ROUTE) {
            DidListScreen(onDidCardTapped = { navController.navigate("${DidsRoutes.DID_DETAIL_ROUTE}/$it") })
        }
        composable("${DidsRoutes.DID_DETAIL_ROUTE}/{${DidsRoutes.DID_PARAM}}") {
            DidDetailsScreen(onGoBack = { navController.popBackStack() })
        }
    }
}

object DidsRoutes {
    const val DID_GRAPH_ROUTE = "did_graph"
    const val DID_LIST_ROUTE = "dids_list"

    const val DID_DETAIL_ROUTE = "did_detail_route"
    const val DID_PARAM = "did"
}