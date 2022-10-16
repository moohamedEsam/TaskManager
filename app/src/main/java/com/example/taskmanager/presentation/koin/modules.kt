package com.example.taskmanager.presentation.koin

import androidx.room.Room
import com.example.taskmanager.data.local.room.AppDatabase
import com.example.taskmanager.data.repository.RepositoryImpl
import com.example.taskmanager.domain.dataModels.data.NoteWithTagsEntity
import com.example.taskmanager.domain.dataModels.data.NoteEntity
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.repository.Repository
import com.example.taskmanager.domain.usecase.note.*
import com.example.taskmanager.presentation.screens.noteForm.NoteFormViewModel
import com.example.taskmanager.presentation.screens.notes.NotesViewModel
import dev.krud.shapeshift.ShapeShiftBuilder
import dev.krud.shapeshift.enums.AutoMappingStrategy
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module

val noteModule = module {
    single { createNoteUseCase(get(), get()) }
    single { updateNoteUseCase(get(), get()) }
    single { deleteNoteUseCase(get(), get()) }
    single { getNotesUseCase(get(), get()) }
    single { getNoteByIdUseCase(get(), get()) }
    viewModel { NotesViewModel(get(), get()) }
    viewModel { params -> NoteFormViewModel(params[0], get(), get(), get()) }
}

val mainModule = module {
    single { provideAppDatabase() }
    single { provideNoteDao(get()) }
    single { provideTagDao(get()) }
    single { provideShapeShift() }
    single<Repository> { RepositoryImpl(get(), get(), get()) }
}

fun provideShapeShift() = ShapeShiftBuilder()
    .withMapping<NoteWithTagsEntity, NoteEntity> {
        autoMap(AutoMappingStrategy.BY_NAME_AND_TYPE)
    }
    .withMapping<NoteWithTagsDto, NoteWithTagsEntity> {
        autoMap(AutoMappingStrategy.BY_NAME_AND_TYPE)
    }
    .build()

fun provideNoteDao(database: AppDatabase) = database.noteDao()

fun provideTagDao(database: AppDatabase) = database.tagDao()

private fun Scope.provideAppDatabase() = Room.databaseBuilder(
    androidContext(),
    AppDatabase::class.java,
    "app_database"
).build()