package dev.mvillasenor.web5.did

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mvillasenor.web5.did.list.DidListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DidListScreen(
    viewModel: DidListViewModel = hiltViewModel(),
    onDidCardTapped: (String) -> Unit
) {
    val state = viewModel.stateFlow.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Manage identities") },
                actions = {
                    IconButton(onClick = { viewModel.onShowDropdown() }) {
                        Icon(Icons.Default.Add, "Create identity")
                    }
                    DropdownMenu(
                        expanded = state.value.dropdownOpened,
                        onDismissRequest = { viewModel.onDismissDropdown() }
                    ) {
                        state.value.supportedKeyTypes.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it.name) },
                                onClick = { viewModel.generateIdentity(it) })
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            items(state.value.dids) { did ->
                DidCard(did = did, onCardTapped = onDidCardTapped)
            }
        }
    }
}