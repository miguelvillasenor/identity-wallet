package dev.mvillasenor.web5.did

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DidCard(
    did: String,
    onCardTapped: (String) -> Unit
) {
    Surface(onClick = { onCardTapped(did) }) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            ListItem(
                headlineText = { Text(text = did, maxLines = 2) },
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(modifier = Modifier.padding(horizontal = 8.dp))
        }

    }

}
