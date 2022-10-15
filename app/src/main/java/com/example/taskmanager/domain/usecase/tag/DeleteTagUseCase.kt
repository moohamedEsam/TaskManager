package com.example.taskmanager.domain.usecase.tag

import com.example.taskmanager.dataModels.NoteWithTagsDto
import com.example.taskmanager.dataModels.Resource
import com.example.taskmanager.dataModels.TagEntity

fun interface DeleteTagUseCase : suspend (TagEntity) -> Resource<Unit>