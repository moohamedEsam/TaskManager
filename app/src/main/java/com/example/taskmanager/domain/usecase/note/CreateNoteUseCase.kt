package com.example.taskmanager.domain.usecase.note

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.repository.Repository
import com.example.taskmanager.presentation.utils.noteBody.NoteMedia
import dev.krud.shapeshift.ShapeShift
import java.io.File
import java.util.*
import kotlin.io.path.Path

fun interface CreateNoteUseCase : suspend (NoteWithTagsDto) -> Resource<Unit>

fun createNoteUseCase(
    context: Context,
    repository: Repository,
    shapeShift: ShapeShift
) =
    CreateNoteUseCase { noteWithTags ->
        val containMedia = noteWithTags.body.find { it is NoteMedia } != null
        if (containMedia) {
            val noteDir = createNoteDirIfNoteExist(context, noteWithTags)
            for (media in noteWithTags.body.filterIsInstance<NoteMedia>()) {
                val newFile = copyMediaFile(noteDir, media, context)
                media.uriString = Uri.fromFile(newFile).toString()
            }
        }
        repository.addNote(shapeShift.map(noteWithTags))
    }

private fun copyMediaFile(
    noteDir: File,
    media: NoteMedia,
    context: Context
): File {
    val imageId = UUID.randomUUID().toString()
    val noteFile = Path(noteDir.absolutePath, imageId).toFile()
    val uri = media.uriString.toUri()
    context.contentResolver.openInputStream(uri)?.use { input ->
        noteFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return noteFile
}

private fun createNoteDirIfNoteExist(
    context: Context,
    noteWithTags: NoteWithTagsDto
): File {
    val noteDir = Path(context.filesDir.absolutePath, noteWithTags.noteId).toFile()
    if (!noteDir.exists())
        noteDir.mkdir()
    return noteDir
}