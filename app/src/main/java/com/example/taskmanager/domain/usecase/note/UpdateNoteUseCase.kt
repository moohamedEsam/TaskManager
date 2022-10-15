package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.repository.Repository
import dev.krud.shapeshift.ShapeShift

fun interface UpdateNoteUseCase : suspend (NoteWithTagsDto) -> Resource<Unit>

fun updateNoteUseCase(repository: Repository, shapeShift: ShapeShift) = UpdateNoteUseCase {
    repository.updateNote(shapeShift.map(it))
}