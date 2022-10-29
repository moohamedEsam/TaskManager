package com.example.taskmanager.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmanager.data.local.room.typeConverters.AttachmentTypeConverter
import com.example.taskmanager.data.local.room.typeConverters.NoteBodyConverter
import com.example.taskmanager.data.models.NoteEntity
import com.example.taskmanager.data.models.NoteWithTagCrossRef
import com.example.taskmanager.data.models.TagEntity

@Database(
    entities = [NoteEntity::class, TagEntity::class, NoteWithTagCrossRef::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AttachmentTypeConverter::class, NoteBodyConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao
}