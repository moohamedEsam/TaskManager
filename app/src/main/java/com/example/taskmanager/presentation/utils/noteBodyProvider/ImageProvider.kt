package com.example.taskmanager.presentation.utils.noteBodyProvider

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.taskmanager.R
import com.example.taskmanager.presentation.composables.RemovableNoteBody
import com.example.taskmanager.presentation.utils.noteBody.NoteBody
import com.example.taskmanager.presentation.utils.noteBody.NoteImage
import org.koin.androidx.compose.get

class ImageProvider() : NoteBodyProvider {
    private var imageBitmap = mutableStateOf<Bitmap?>(null)

    @Composable
    override fun Draw(modifier: Modifier, onRemove: () -> Unit) {
        RemovableNoteBody(onRemove = onRemove) {
            val context = LocalContext.current
            val imageLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
            ) {
                if (it == null) return@rememberLauncherForActivityResult
                imageBitmap.value =
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            context.contentResolver,
                            it
                        )
                    )
            }
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
            ) {
                if (!it) return@rememberLauncherForActivityResult
                imageLauncher.launch("image/*")
            }
            Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = imageBitmap.value ?: R.drawable.image,
                    contentDescription = null,
                    imageLoader = get(),
                    modifier = Modifier
                        .heightIn(
                            100.dp,
                            (LocalConfiguration.current.screenHeightDp / 3).dp
                        )
                        .clickable {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        },
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    override fun getNoteBody(): NoteBody = NoteImage("")
}