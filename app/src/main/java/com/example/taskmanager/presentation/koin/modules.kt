package com.example.taskmanager.presentation.koin

import android.os.Build
import androidx.room.Room
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.taskmanager.data.local.room.AppDatabase
import com.example.taskmanager.data.repository.RepositoryImpl
import com.example.taskmanager.domain.dataModels.data.NoteWithTagsEntity
import com.example.taskmanager.domain.dataModels.data.NoteEntity
import com.example.taskmanager.domain.dataModels.data.TagEntity
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.dataModels.presentation.TagDto
import com.example.taskmanager.domain.repository.Repository
import com.example.taskmanager.domain.usecase.note.*
import com.example.taskmanager.domain.usecase.tag.createTagUseCase
import com.example.taskmanager.presentation.screens.noteForm.NoteFormViewModel
import com.example.taskmanager.presentation.screens.noteDetailsScreen.NoteDetailsViewModel
import com.example.taskmanager.presentation.screens.notes.NotesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module

val noteModule = module {
    single { createNoteUseCase(androidContext(), get()) }
    single { updateNoteUseCase(androidContext(), get()) }
    single { deleteNoteUseCase(get()) }
    single { getNotesUseCase(get()) }
    single { getNoteByIdUseCase(get()) }
    single { UpdateNoteArchiveUseCase(get<Repository>()::updateNoteArchived) }
    single { UpdateNotePinUseCase(get<Repository>()::updateNotePinned) }
    single { UpdateNoteFavoriteUseCase(get<Repository>()::updateNoteFavorite) }
    single { UpdateNoteDeleteUseCase(get<Repository>()::updateNoteDeleted) }
    viewModel { NotesViewModel(get(), get(), get(), get(), get()) }
    viewModel { params -> NoteDetailsViewModel(get(), get(), get(), get(), get(), params[0]) }
    viewModel { params -> NoteFormViewModel(params[0], get(), get(), get(), get()) }
}

val tagModule = module {
    single { createTagUseCase(get()) }
}

val mainModule = module {
    single { provideImageLoader() }
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

fun Scope.provideImageLoader() = ImageLoader
    .Builder(androidContext())
    .components {
        if (Build.VERSION.SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }
    .crossfade(true)
    .build()
