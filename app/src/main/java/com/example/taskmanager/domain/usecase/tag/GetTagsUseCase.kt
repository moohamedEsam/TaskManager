package com.example.taskmanager.domain.usecase.tag

import com.example.taskmanager.domain.models.Tag
import com.example.taskmanager.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun interface GetTagsUseCase : () -> Flow<List<Tag>>
