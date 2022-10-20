package com.example.taskmanager.presentation.utils.noteBody

import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import java.io.File

@kotlinx.serialization.Serializable
class NoteImage(private val path: String?) : NoteBody {
    @Composable
    override fun Draw(modifier: Modifier) {
        val context = LocalContext.current
        val bitmap by remember {
            mutableStateOf(
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        context.contentResolver,
                        Uri.parse(path ?: "")
                    )
                )
            )
        }
        AsyncImage(
            model = bitmap,
            contentDescription = null,
            modifier = modifier
        )
    }

}