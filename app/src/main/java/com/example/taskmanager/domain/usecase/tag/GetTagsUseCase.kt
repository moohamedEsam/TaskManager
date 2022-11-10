package com.example.taskmanager.domain.usecase.tag

import com.example.taskmanager.domain.models.tag.Tag
import kotlinx.coroutines.flow.Flow

fun interface GetTagsUseCase : () -> Flow<List<Tag>>
