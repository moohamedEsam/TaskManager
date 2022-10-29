package com.example.taskmanager.domain.usecase.tag

import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.Tag
import com.example.taskmanager.domain.models.asEntity
import com.example.taskmanager.domain.repository.TagRepository

fun interface CreateTagUseCase : suspend (Tag) -> Resource<Unit>
