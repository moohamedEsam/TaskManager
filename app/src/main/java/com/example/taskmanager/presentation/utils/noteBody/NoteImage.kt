package com.example.taskmanager.presentation.utils.noteBody

import android.graphics.ImageDecoder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import java.io.File

class NoteImage(private val path: String) : NoteBody {
        @Composable
        override fun Draw(modifier: Modifier) {
            val bitmap by remember {
                mutableStateOf(ImageDecoder.decodeBitmap(ImageDecoder.createSource(File(path))))
            }
            AsyncImage(
                model = bitmap,
                contentDescription = null,
                modifier = modifier
            )
        }

}