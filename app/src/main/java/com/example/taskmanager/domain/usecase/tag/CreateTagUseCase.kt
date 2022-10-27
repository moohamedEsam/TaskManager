package com.example.taskmanager.domain.usecase.tag

import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.data.TagEntity
import com.example.taskmanager.domain.dataModels.presentation.TagDto
import com.example.taskmanager.domain.repository.Repository

fun interface CreateTagUseCase : suspend (TagDto) -> Resource<Unit>

fun createTagUseCase(repository: Repository) = CreateTagUseCase {
    repository.addTag(it.toEntity())
}