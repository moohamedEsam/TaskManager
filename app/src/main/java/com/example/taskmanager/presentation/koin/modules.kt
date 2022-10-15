package com.example.taskmanager.presentation.koin

import androidx.room.Room
import com.example.taskmanager.data.local.room.AppDatabase
import com.example.taskmanager.data.repository.RepositoryImpl
import com.example.taskmanager.domain.repository.Repository
import com.example.taskmanager.domain.usecase.note.*
import com.example.taskmanager.presentation.screens.notes.NotesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module

val noteModule = module {
    single { createNoteUseCase(get()) }
    single { updateNoteUseCase(get()) }
    single { deleteNoteUseCase(get()) }
    single { GetNotesUseCase(get<Repository>()::getNotes) }
    viewModel { NotesViewModel(get(), get()) }
}

val mainModule = module {
    single { provideAppDatabase() }
    single { provideNoteDao(get()) }
    single { provideTagDao(get()) }
    single<Repository> { RepositoryImpl(get(), get()) }
}

fun provideNoteDao(database: AppDatabase) = database.noteDao()

fun provideTagDao(database: AppDatabase) = database.tagDao()

private fun Scope.provideAppDatabase() = Room.databaseBuilder(
    androidContext(),
    AppDatabase::class.java,
    "app_database"
).build()
