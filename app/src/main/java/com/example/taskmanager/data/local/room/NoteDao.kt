package com.example.taskmanager.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.taskmanager.dataModels.NoteEntity
import com.example.taskmanager.dataModels.NoteWithTagCrossRef
import com.example.taskmanager.dataModels.NoteWithTagsDto
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteWithTagsDto>>

    @Transaction
    @Query("SELECT * FROM notes WHERE noteId = :id")
    fun getNoteById(id: String): Flow<NoteWithTagsDto?>

    @Insert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNoteById(noteEntity: NoteEntity)

    @Query("delete from noteWithTagCrossRef where noteId = :noteId")
    suspend fun deleteNoteTags(noteId: String)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Insert
    suspend fun addTagToNote(note: NoteWithTagCrossRef)

    @Delete
    suspend fun deleteTagFromNote(note: NoteWithTagCrossRef)
}