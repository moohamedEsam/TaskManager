package com.example.taskmanager.presentation.screens.reminders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taskmanager.R
import com.example.taskmanager.domain.models.reminder.Reminder
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat

@Composable
fun RemindersScreen(
    onReminderClick: (String) -> Unit,
    viewModel: RemindersViewModel = koinViewModel()
) {
    val reminders by viewModel.reminders.collectAsState()
    RemindersScreen(
        reminders = reminders,
        onReminderClick = { onReminderClick(it.reminderId) },
        onReminderPinClick = viewModel::updateReminderPin,
        onReminderFavoriteClick = viewModel::updateReminderFavorite,
        onReminderArchiveClick = viewModel::updateReminderArchive,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RemindersScreen(
    reminders: List<Reminder>,
    onReminderClick: (Reminder) -> Unit,
    onReminderPinClick: (Reminder) -> Unit,
    onReminderFavoriteClick: (Reminder) -> Unit,
    onReminderArchiveClick: (Reminder) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Search Reminders") },
            modifier = Modifier
                .fillMaxWidth()
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reminders, key = { it.reminderId }) {
                ReminderCardItem(
                    reminder = it,
                    onReminderClick = { onReminderClick(it) },
                    onReminderPinClick = { onReminderPinClick(it) },
                    onReminderFavoriteClick = { onReminderFavoriteClick(it) },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderCardItem(
    reminder: Reminder,
    modifier: Modifier = Modifier,
    onReminderClick: () -> Unit,
    onReminderPinClick: () -> Unit,
    onReminderFavoriteClick: () -> Unit,
) {
    val simpleDateFormat by remember {
        mutableStateOf(SimpleDateFormat("dd MMM hh:mm a"))
    }
    OutlinedCard(
        onClick = onReminderClick,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = reminder.title)
            Text(reminder.description, maxLines = 2)
            Text(text = "Remind Time: ${simpleDateFormat.format(reminder.date)}")
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onReminderPinClick) {
                    if (reminder.isPinned)
                        Icon(
                            painterResource(id = R.drawable.pin_filled),
                            contentDescription = "Unpin reminder",
                            modifier = Modifier.size(24.dp)
                        )
                    else
                        Icon(
                            painterResource(id = R.drawable.pin),
                            contentDescription = "Pin reminder",
                            modifier = Modifier.size(24.dp)
                        )
                }

                IconButton(onClick = onReminderFavoriteClick) {
                    if (reminder.isFavorite)
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Unfavorite reminder",
                        )
                    else
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite reminder"
                        )
                }
            }
        }


    }


}
