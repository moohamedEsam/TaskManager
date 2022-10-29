package com.example.taskmanager.presentation.di

import android.os.Build
import androidx.room.Room
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.taskmanager.data.local.room.AppDatabase
import com.example.taskmanager.data.repository.NoteRepositoryImpl
import com.example.taskmanager.data.repository.TagRepositoryImpl
import com.example.taskmanager.domain.repository.NoteRepository
import com.example.taskmanager.domain.repository.TagRepository
import com.example.taskmanager.domain.usecase.note.*
import com.example.taskmanager.domain.usecase.tag.CreateTagUseCase
import com.example.taskmanager.domain.usecase.tag.GetTagsUseCase
import com.example.taskmanager.presentation.screens.noteForm.NoteFormViewModel
import com.example.taskmanager.presentation.screens.noteDetailsScreen.NoteDetailsViewModel
import com.example.taskmanager.presentation.screens.notes.NotesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module

val noteModule = module {
    single<NoteRepository> { NoteRepositoryImpl(get()) }
    single { createNoteUseCase(androidContext(), get()) }
    single { updateNoteUseCase(androidContext(), get()) }
    single { DeleteNoteUseCase(get<NoteRepository>()::deleteNote) }
    single { GetNotesUseCase(get<NoteRepository>()::getNotes) }
    single { GetNoteByIdUseCase(get<NoteRepository>()::getNoteById) }
    single { UpdateNoteArchiveUseCase(get<NoteRepository>()::updateNoteArchived) }
    single { UpdateNotePinUseCase(get<NoteRepository>()::updateNotePinned) }
    single { UpdateNoteFavoriteUseCase(get<NoteRepository>()::updateNoteFavorite) }
    single { UpdateNoteDeleteUseCase(get<NoteRepository>()::updateNoteDeleted) }
    viewModel { NotesViewModel(get(), get(), get(), get(), get()) }
    viewModel { params -> NoteDetailsViewModel(get(), get(), get(), get(), get(), params[0]) }
    viewModel { params -> NoteFormViewModel(params[0], get(), get(), get(), get(), get()) }
}

val tagModule = module {
    single<TagRepository> { TagRepositoryImpl(get()) }
    single { CreateTagUseCase(get<TagRepository>()::addTag) }
    single { GetTagsUseCase(get<TagRepository>()::getTags) }
}

val mainModule = module {
    single { provideImageLoader() }
    single { provideAppDatabase() }
    single { provideNoteDao(get()) }
    single { provideTagDao(get()) }
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
