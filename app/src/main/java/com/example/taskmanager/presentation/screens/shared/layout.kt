package com.example.taskmanager.presentation.screens.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.presentation.navigation.Navigation
import com.example.taskmanager.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout() {
    val navHostController = rememberNavController()
    val snackbarHostState by remember {
        mutableStateOf(SnackbarHostState())
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = {
            Navigation(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState,
                paddingValues = it
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(Screens.CreateNoteScreen.withArgs("  "))
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    )
}
