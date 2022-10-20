package com.example.taskmanager.data.local.room.typeConverters

import androidx.room.TypeConverter
import com.example.taskmanager.presentation.utils.noteBody.NoteBody
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NoteBodyConverter {
    @TypeConverter
    fun fromNoteBody(noteBodies: List<NoteBody>) = Json.encodeToString(noteBodies)

    @TypeConverter
    fun toNoteBody(noteBodies: String) = Json.decodeFromString<List<NoteBody>>(noteBodies)
}