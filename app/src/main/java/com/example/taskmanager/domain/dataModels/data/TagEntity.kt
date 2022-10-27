package com.example.taskmanager.domain.dataModels.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.domain.dataModels.interfaces.Tag
import com.example.taskmanager.domain.dataModels.presentation.TagDto
import java.util.*

@Entity(tableName = "tags")
data class TagEntity(
    override val name: String,
    @PrimaryKey
    override val tagId: String = UUID.randomUUID().toString()
) : Tag{
    fun toDomain() = TagDto(name, tagId)
}
