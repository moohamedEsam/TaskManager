package com.example.taskmanager.presentation.utils.noteBody

import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.taskmanager.presentation.utils.noteBodyProvider.ImageProvider
import com.example.taskmanager.presentation.utils.noteBodyProvider.NoteBodyProvider

@kotlinx.serialization.Serializable
class NoteImage(override var uriString: String) : NoteMedia {

    @Composable
    override fun Draw(modifier: Modifier) {
        val context = LocalContext.current
        val bitmap by remember {
            mutableStateOf(
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        context.contentResolver,
                        uriString.toUri()
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

    override fun getProvider(): NoteBodyProvider = ImageProvider(Uri.parse(uriString))

}