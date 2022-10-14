package com.example.taskmanager.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.domain.dataModels.Tag
import java.util.*

@Entity(tableName = "tags")
data class TagEntity(
    override val name: String,
    override val color: Int,
    @PrimaryKey
    override val tagId: String = UUID.randomUUID().toString()
) : Tag
