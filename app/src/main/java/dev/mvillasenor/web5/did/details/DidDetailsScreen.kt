package dev.mvillasenor.web5.did.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DidDetailsScreen(
    viewModel: DidDetailsViewModel = hiltViewModel(),
    onGoBack: () -> Unit
) {
    val state = viewModel.stateFlow.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Did details") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                navigationIcon = {
                    IconButton(onClick = { onGoBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "go back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            state.value.didDocument?.let { document ->

                ListItem(
                    overlineText = { Text(text = "Context: ") },
                    headlineText = { Text(text = document.context.joinToString(", ")) },
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    overlineText = { Text(text = "Id:") },
                    headlineText = { Text(text = document.id) },
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    overlineText = { Text(text = "Controller:") },
                    headlineText = { Text(text = document.controller) },
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    overlineText = { Text(text = "AlsoKnownAs:") },
                    headlineText = { Text(text = document.alsoKnownAs) },
                )
                Divider(modifier = Modifier.padding(horizontal = 16.dp))

                Button(
                    onClick = {
                        viewModel.removeDid()
                        onGoBack()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Remove")
                }
            }

        }
    }
}