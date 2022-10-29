package com.example.taskmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.domain.models.Tag
import java.util.*

@Entity(tableName = "tags")
data class TagEntity(
    val name: String,
    @PrimaryKey
    val tagId: String = UUID.randomUUID().toString()
)

fun TagEntity.asDomain() = Tag(name, tagId)
