package com.example.taskmanager.domain.usecase.tag

import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.data.TagEntity

fun interface CreateTagUseCase : suspend (TagEntity) -> Resource<Unit>