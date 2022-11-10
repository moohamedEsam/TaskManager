package com.example.taskmanager.domain.usecase.tag

import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.data.models.tag.TagEntity

fun interface DeleteTagUseCase : suspend (TagEntity) -> Resource<Unit>