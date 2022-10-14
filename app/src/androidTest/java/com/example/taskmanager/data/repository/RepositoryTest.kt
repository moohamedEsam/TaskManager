package com.example.taskmanager.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.taskmanager.data.local.room.AppDatabase
import com.example.taskmanager.dataModels.NoteEntity
import com.example.taskmanager.dataModels.NoteWithTagsDto
import com.example.taskmanager.dataModels.TagEntity
import com.example.taskmanager.domain.dataModels.Attachment
import com.example.taskmanager.domain.repository.Repository
import com.google.common.truth.Truth.assertThat
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
class RepositoryTest {
    private lateinit var db: AppDatabase
    private lateinit var repository: Repository

    @OptIn(DelicateCoroutinesApi::class)
    private var mainThread = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThread)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repository = RepositoryImpl(db.noteDao(), db.tagDao())
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
        val note = NoteWithTagsDto(
            NoteEntity(title = "title", description = "description", emptyList()),
            listOf(tag)
        )
        repository.addNote(note)
        repository.getNotes().test {
            val notes = awaitItem()
            assertThat(notes).hasSize(1)
            assertThat(notes.first().tags).hasSize(1)
        }
    }


    @Test
    fun addNote() = runTest {
        val note = NoteWithTagsDto(NoteEntity("title", "description", emptyList()), emptyList())
        repository.addNote(note)
        repository.getNotes().test {
            val notes = awaitItem()
            assertThat(notes).isNotEmpty()
            assertThat(notes.first().note.title).isEqualTo(note.note.title)
            assertThat(notes.first().tags).isEmpty()
        }
    }

    @Test
    fun updateNote() = runTest {
        val note = NoteWithTagsDto(NoteEntity("title", "description", emptyList()), emptyList())
        repository.addNote(note)
        val updatedNote = note.copy(
            note = note.note.copy(
                title = "updated title",
                attachments = listOf(Attachment.Audio("path"))
            ),
            tags = listOf(TagEntity("tag", 1))
        )
        repository.updateNote(updatedNote)
        repository.getNotes().test {
            val notes = awaitItem()
            assertThat(notes).isNotEmpty()
            assertThat(notes.first().note.title).isEqualTo(updatedNote.note.title)
            assertThat(notes.first().tags).hasSize(1)
            assertThat(notes.first().note.attachments).hasSize(1)
        }
    }
}