package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.repository.Repository
import dev.krud.shapeshift.ShapeShift

fun interface CreateNoteUseCase : suspend (NoteWithTagsDto) -> Resource<Unit>

fun createNoteUseCase(repository: Repository, shapeShift: ShapeShift) = CreateNoteUseCase {
        repository.addNote(shapeShift.map(it))
    }