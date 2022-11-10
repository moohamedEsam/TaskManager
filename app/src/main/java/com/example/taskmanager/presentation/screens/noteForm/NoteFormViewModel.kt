package com.example.taskmanager.presentation.screens.noteForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.models.note.NoteWithTags
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.SnackBarEvent
import com.example.taskmanager.domain.models.tag.Tag
import com.example.taskmanager.domain.usecase.note.CreateNoteUseCase
import com.example.taskmanager.domain.usecase.note.GetNoteByIdUseCase
import com.example.taskmanager.domain.usecase.note.UpdateNoteUseCase
import com.example.taskmanager.domain.usecase.tag.CreateTagUseCase
import com.example.taskmanager.domain.usecase.tag.GetTagsUseCase
import com.example.taskmanager.presentation.utils.noteBodyProvider.NoteBodyProvider
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class NoteFormViewModel(
    private val noteId: String,
    private val createNoteUseCase: CreateNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val createTagUseCase: CreateTagUseCase,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {
    private val _noteTitle = MutableStateFlow("")
    val noteTitle = _noteTitle.asStateFlow()
    private val _tags = MutableStateFlow(emptyList<Tag>())
    val tags = _tags.asStateFlow()
    private val _noteBodies = MutableStateFlow(emptyList<NoteBodyProvider>())
    val noteBodies = _noteBodies.asStateFlow()
    val showActionButtons = combine(_noteTitle, _noteBodies) { title, bodies ->
        title.isNotBlank() && bodies.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val snackBarChannel = Channel<SnackBarEvent>()
    val receiveChannel = snackBarChannel.receiveAsFlow()

    private val _showTagDialog = MutableStateFlow(false)
    val showTagDialog = _showTagDialog.asStateFlow()

    private val _noteTags = MutableStateFlow(emptySet<Tag>())
    val noteTags = _noteTags.asStateFlow()
    private val createdTags = mutableListOf<String>()

    init {
        getNote()
        getTags()
    }

    @OptIn(FlowPreview::class)
    private fun getNote() = viewModelScope.launch {
        if (noteId.isNotBlank()) {
            getNoteByIdUseCase(noteId).debounce(200).collectLatest { noteWithTagsDto ->
                if (noteWithTagsDto == null) return@collectLatest
                _noteTitle.update { noteWithTagsDto.title }
                _noteBodies.update { _ ->
                    noteWithTagsDto.body.map { it.getProvider() }
                }
                _noteTags.update { _ -> noteWithTagsDto.tags.toSet() }
            }
        }
    }

    private fun getTags() = viewModelScope.launch {
        getTagsUseCase().collectLatest { tags ->
            _tags.update { _ -> tags }
        }
    }

    fun addNoteBody(noteBody: NoteBodyProvider) {
        _noteBodies.update { it + noteBody }
    }

    fun removeNoteBody(noteBody: NoteBodyProvider) {
        _noteBodies.update { it - noteBody }
    }

    fun saveNote(onNoteSaved: (String) -> Unit = {}): Job = viewModelScope.launch {
        val note = NoteWithTags(
            _noteTitle.value,
            _noteBodies.value.map { it.getNoteBody() },
            attachments = emptyList(),
            tags = _noteTags.value.toList(),
            noteId = noteId.ifBlank { UUID.randomUUID().toString() }
        )
        _noteTags.value.filter { it.tagId in createdTags }.forEach {
            createTagUseCase(it)
        }
        val result = if (noteId.isBlank())
            createNoteUseCase(note)
        else
            updateNoteUseCase(note)
        val event = when (result) {
            is Resource.Error -> SnackBarEvent(result.message ?: "") { saveNote() }
            is Resource.Success -> {
                SnackBarEvent("note has been saved", "View") {
                    onNoteSaved(note.noteId)
                }
            }
            else -> null
        }
        if (event != null)
            snackBarChannel.send(event)
    }

    fun createTag(tagName: String) = viewModelScope.launch {
        val tag = Tag(tagName)
        createdTags.add(tag.tagId)
        addTag(tag)
    }

    fun addTag(tag: Tag) {
        _noteTags.update { it + tag }
    }

    fun removeTag(tag: Tag) {
        _noteTags.update { it - tag }
    }

    fun updateNoteTitle(value: String) {
        _noteTitle.update { value }
    }

    fun toggleTagDialog() {
        _showTagDialog.update { !it }
    }
}