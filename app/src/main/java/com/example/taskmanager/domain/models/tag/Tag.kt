package com.example.taskmanager.domain.models.tag

import com.example.taskmanager.data.models.tag.TagEntity
import java.util.UUID

data class Tag(
    val name: String,
    val tagId: String = UUID.randomUUID().toString(),
)

fun Tag.asEntity() = TagEntity(name, tagId)
