package com.example.taskmanager.domain.usecase.note

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.presentation.utils.noteBody.NoteMedia
import java.io.File
import java.util.*
import kotlin.io.path.Path

fun copyMediaFile(
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

fun createNoteDirIfNoteExist(
    context: Context,
    noteWithTags: NoteWithTagsDto
): File {
    val noteDir = getNoteDir(context, noteWithTags.noteId)
    if (!noteDir.exists())
        noteDir.mkdir()
    return noteDir
}

fun deleteNoteDir(
    context: Context,
    noteWithTags: NoteWithTagsDto
) {
    val noteDir = getNoteDir(context, noteWithTags.noteId)
    if (noteDir.exists())
        noteDir.deleteRecursively()
}

fun updateNoteDirAndBody(
    context: Context,
    noteWithTags: NoteWithTagsDto
) {
    val noteDir = createNoteDirIfNoteExist(context, noteWithTags)
    val currentNoteFiles = mutableSetOf<String>()
    for (media in noteWithTags.body.filterIsInstance<NoteMedia>()) {
        val noteFile = copyMediaFile(noteDir, media, context)
        media.uriString = Uri.fromFile(noteFile).toString()
        currentNoteFiles.add(noteFile.name)
    }
    noteDir.listFiles()?.filter { it.name !in currentNoteFiles }?.forEach { it.delete() }
}

private fun getNoteDir(
    context: Context,
    noteId: String
) = Path(context.filesDir.absolutePath, noteId).toFile()