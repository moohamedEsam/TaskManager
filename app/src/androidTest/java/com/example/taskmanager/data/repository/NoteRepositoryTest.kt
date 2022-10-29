package com.example.taskmanager.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.taskmanager.data.local.room.AppDatabase
import com.example.taskmanager.data.models.NoteEntity
import com.example.taskmanager.data.models.NoteWithTagsEntity
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.data.models.TagEntity
import com.example.taskmanager.domain.models.Attachment
import com.example.taskmanager.domain.repository.NoteRepository
import com.google.common.truth.Truth.assertThat
import dev.krud.shapeshift.ShapeShift
import dev.krud.shapeshift.ShapeShiftBuilder
import dev.krud.shapeshift.enums.AutoMappingStrategy
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class NoteRepositoryTest {
    private lateinit var db: AppDatabase
    private lateinit var noteRepository: NoteRepository
    private lateinit var shapeShift: ShapeShift

    @OptIn(DelicateCoroutinesApi::class)
    private var mainThread = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThread)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        shapeShift = ShapeShiftBuilder()
            .withMapping<NoteWithTagsEntity, NoteEntity> {
                autoMap(AutoMappingStrategy.BY_NAME_AND_TYPE)
            }
            .build()
        noteRepository = NoteRepositoryImpl(db.noteDao(), db.tagDao(), shapeShift)
    }

    @After
    fun tearDown() {
        db.close()
        Dispatchers.resetMain()
        mainThread.close()
    }

    @Test
    fun getNotes() = runTest {
        val tag = TagEntity("tag", 1)
        val note = NoteWithTagsEntity(
            title = "title",
            description = "description",
            attachments = emptyList(),
            tags = listOf(tag)
        )
        noteRepository.addNote(note)
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertThat(notes).hasSize(1)
            assertThat(notes.first().tags).hasSize(1)
        }
    }


    @Test
    fun addNote() = runTest {
        val note = NoteWithTagsEntity(
            title = "title", description = "description",
            attachments = emptyList(),
            tags = emptyList()
        )
        val result = noteRepository.addNote(note)
        assertThat(result).isInstanceOf(Resource.Success::class.java)
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertThat(notes).isNotEmpty()
            assertThat(notes.first().title).isEqualTo(note.title)
            assertThat(notes.first().tags).isEmpty()
        }
    }

    @Test
    fun updateNote() = runTest {
        val note = NoteWithTagsEntity(
            title = "title", description = "description",
            attachments = emptyList(),
            tags = emptyList()
        )
        noteRepository.addNote(note)
        val updatedNote = note.copy(
            title = "updated title",
            attachments = listOf(Attachment.Audio("path")),
            tags = listOf(TagEntity("tag", 1))
        )
        noteRepository.updateNote(updatedNote)
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertThat(notes).isNotEmpty()
            assertThat(notes.first().title).isEqualTo(updatedNote.title)
            assertThat(notes.first().tags).hasSize(1)
            assertThat(notes.first().attachments).hasSize(1)
        }
    }
}