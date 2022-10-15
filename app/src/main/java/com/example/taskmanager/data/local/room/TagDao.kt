package com.example.taskmanager.data.local.room

import androidx.room.*
import com.example.taskmanager.domain.dataModels.data.TagEntity
import com.example.taskmanager.domain.dataModels.data.TagWithNotesEntity
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

    @Transaction
    @Query("SELECT * FROM tags WHERE tagId = :id")
    suspend fun getTagSnapShotById(id: String): TagWithNotesEntity?

    @Transaction
    @Query("SELECT * FROM tags WHERE tagId = :id")
    fun getNotesByTagId(id: String): Flow<List<TagWithNotesEntity>>
}