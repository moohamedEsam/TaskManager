package com.example.taskmanager.presentation.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taskmanager.R

@Composable
fun ActionBar(
    title: String,
    isPinned: Boolean,
    isFavorite: Boolean,
    isDeleted: Boolean,
    onEditClick: () -> Unit,
    onPinClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier

) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.weight(0.8f))

        IconButton(onClick = onEditClick) {
            Icon(Icons.Outlined.Edit, contentDescription = "edit")
        }
        IconButton(onClick = onPinClick) {
            Icon(
                painter = if (isPinned)
                    painterResource(id = R.drawable.pin_filled)
                else
                    painterResource(id = R.drawable.pin),
                contentDescription = "pin",
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(onClick = onFavoriteClick) {
            if (isFavorite)
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "favorite")
            else
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "favorite")
        }
        IconButton(onClick = onDeleteClick) {
            if (isDeleted)
                Icon(Icons.Filled.Delete, contentDescription = "delete")
            else
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "delete")
        }

    }
}
