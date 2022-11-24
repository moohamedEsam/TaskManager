package com.example.taskmanager.presentation.screens.shared

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.presentation.navigation.Navigation
import com.example.taskmanager.presentation.navigation.Screens
import com.example.taskmanager.presentation.screens.archivedNotes.navigateToArchivedNotesScreen
import com.example.taskmanager.presentation.screens.noteForm.navigateToNoteFormScreen
import com.example.taskmanager.presentation.screens.notes.navigateToNotesScreen
import com.example.taskmanager.presentation.screens.notes.notesScreenRoute
import com.example.taskmanager.presentation.screens.reminderForm.navigateToReminderFormScreen
import com.example.taskmanager.presentation.screens.reminders.navigateToRemindersScreen
import com.example.taskmanager.presentation.screens.reminders.remindersScreenRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout() {
    val navHostController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState by remember {
        mutableStateOf(SnackbarHostState())
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = {
            ModalNavigationDrawer(
                drawerContent = { DrawerContent(navHostController = navHostController) },
                drawerState = drawerState,
                modifier = Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
            ) {
                Navigation(
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState
                )
            }
        },
        topBar = {
            TopAppBarContent(navHostController, drawerState)
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopAppBarContent(
    navHostController: NavHostController,
    drawerState: DrawerState
) {
    val currentDestination by navHostController.currentBackStackEntryAsState()
    TopAppBar(
        title = {
            Text(currentDestination?.destination?.route?.takeWhile { it != '/' } ?: "task manager")
        },
        navigationIcon = {
            NavigationIcon(navHostController, drawerState)
        },
        actions = {
            IconButton(onClick = navHostController::navigateToNoteFormScreen) {
                Icon(Icons.Filled.NoteAdd, contentDescription = "Add note")
            }

            IconButton(onClick = navHostController::navigateToReminderFormScreen) {
                Icon(Icons.Filled.AlarmAdd, contentDescription = "Add reminder")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationIcon(navHostController: NavHostController, drawerState: DrawerState) {
    val currentRoute by navHostController.currentBackStackEntryAsState()
    val coroutine = rememberCoroutineScope()
    if (currentRoute?.destination?.route !in listOf(
            Screens.notesScreenRoute(),
            Screens.remindersScreenRoute()
        )
    )
        IconButton(onClick = navHostController::popBackStack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
    else
        IconButton(onClick = {
            coroutine.launch {
                if (drawerState.isClosed)
                    drawerState.open()
                else
                    drawerState.close()
            }
        }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null
            )
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerContent(navHostController: NavHostController) {
    val currentDestination by navHostController.currentBackStackEntryAsState()
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth(0.9f)
            .padding(8.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ExpandableBox(
            title = "Notes",
            selected = currentDestination?.destination?.route == Screens.notesScreenRoute(),
            icon = { Icon(imageVector = Icons.Default.Notes, contentDescription = null) },
            onTitleClick = navHostController::navigateToNotesScreen
        ) {
            NavigationDrawerItem(
                label = { Text("Archived") },
                selected = false,
                onClick = navHostController::navigateToArchivedNotesScreen
            )
        }

        NavigationDrawerItem(
            label = { Text("Reminders") },
            selected = currentDestination?.destination?.route == Screens.remindersScreenRoute(),
            icon = { Icon(imageVector = Icons.Default.Alarm, contentDescription = null) },
            onClick = navHostController::navigateToRemindersScreen
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandableBox(
    title: String,
    selected: Boolean = false,
    icon: @Composable () -> Unit = {},
    onTitleClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NavigationDrawerItem(
            label = { Text(title) },
            selected = selected,
            onClick = {
                expanded = !expanded
                onTitleClick()
            },
            icon = icon,
            badge = {
                IconButton(onClick = { expanded = !expanded }) {
                    if (expanded)
                        Icon(imageVector = Icons.Default.ExpandLess, contentDescription = null)
                    else
                        Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null)
                }
            }
        )
        if (expanded)
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                content()
            }
    }

}