package com.example.taskmanager.data.local.room.typeConverters

import androidx.room.TypeConverter
import com.example.taskmanager.domain.dataModels.interfaces.Attachment
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class AttachmentTypeConverter {
    @TypeConverter
    fun fromJson(attachments: List<Attachment>): String {
        return Json.encodeToString(attachments)
    }

    @TypeConverter
    fun fromList(attachments: String): List<Attachment> {
        return Json.decodeFromString(attachments)
    }
}