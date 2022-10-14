package com.example.taskmanager.data.local.room

import androidx.room.*
import com.example.taskmanager.dataModels.TagEntity
import com.example.taskmanager.dataModels.TagWithNotesDto
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
    @Insert
    suspend fun insertTag(tag: TagEntity)

    @Update
    suspend fun updateTag(tag: TagEntity)

    @Delete
    suspend fun deleteTag(tag: TagEntity)

    @Query("SELECT * FROM tags")
    fun getTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tags WHERE tagId = :id")
    fun getTagById(id: String): Flow<TagEntity?>

    @Query("SELECT * FROM tags WHERE tagId = :id")
    suspend fun getTagSnapShotById(id: String): TagWithNotesDto?

    @Transaction
    @Query("SELECT * FROM tags WHERE tagId = :id")
    fun getNotesByTagId(id: String): Flow<List<TagWithNotesDto>>
}