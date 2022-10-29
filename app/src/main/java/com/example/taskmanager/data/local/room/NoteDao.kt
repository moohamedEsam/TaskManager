package com.example.taskmanager.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.taskmanager.data.models.NoteEntity
import com.example.taskmanager.data.models.NoteWithTagCrossRef
import com.example.taskmanager.data.models.NoteWithTagsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteWithTagsEntity>>

    @Transaction
    @Query("SELECT * FROM notes WHERE noteId = :id")
    fun getNoteById(id: String): Flow<NoteWithTagsEntity?>

    @Insert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNoteById(noteEntity: NoteEntity)

    @Query("delete from noteWithTagCrossRef where noteId = :noteId")
    suspend fun deleteNoteTags(noteId: String)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Query("update notes set isFavorite=:isFavorite where noteId=:noteId")
    suspend fun markNoteAsFavorite(noteId: String, isFavorite: Boolean)

    @Query("update notes set isPinned=:isPinned where noteId=:noteId")
    suspend fun markNoteAsPinned(noteId: String, isPinned: Boolean)

    @Query("update notes set isArchived=:isArchived where noteId=:noteId")
    suspend fun markNoteAsArchived(noteId: String, isArchived: Boolean)

    @Query("update notes set isDeleted=:isDeleted where noteId=:noteId")
    suspend fun markNoteAsDeleted(noteId: String, isDeleted: Boolean)

    @Insert
    suspend fun addTagToNote(note: NoteWithTagCrossRef)

    @Query("delete from noteWithTagCrossRef where noteId = :noteId")
    suspend fun deleteNoteTagsRefs(noteId: String)

    @Delete
    suspend fun deleteTagFromNote(note: NoteWithTagCrossRef)
}