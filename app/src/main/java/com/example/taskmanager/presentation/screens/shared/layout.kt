package com.example.taskmanager.presentation.screens.shared

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.presentation.navigation.Navigation

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
        }
    )
}
