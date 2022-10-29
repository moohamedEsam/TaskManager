package com.example.taskmanager.domain.utils

import android.util.Log
import com.example.taskmanager.domain.models.Resource

suspend fun <T> mapResultToResource(result: suspend () -> T) = try {
    Resource.Success(result())
} catch (e: Exception) {
    Log.e("mapToResource", "mapResultToResource: ${e.message}")
    Resource.Error(e.message ?: "Unknown error")
}