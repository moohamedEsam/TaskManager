package com.example.taskmanager.presentation.screens.reminderForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.SnackBarEvent
import com.example.taskmanager.domain.models.reminder.Reminder
import com.example.taskmanager.domain.models.tag.Tag
import com.example.taskmanager.domain.usecase.reminder.CreateReminderUseCase
import com.example.taskmanager.domain.usecase.reminder.GetReminderUseCase
import com.example.taskmanager.domain.usecase.reminder.UpdateReminderUseCase
import com.example.taskmanager.domain.usecase.tag.CreateTagUseCase
import com.example.taskmanager.domain.usecase.tag.GetTagsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class ReminderFormViewModel(
    private val reminderId: String,
    private val createReminderUseCase: CreateReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase,
    private val getReminderUseCase: GetReminderUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val createTagUseCase: CreateTagUseCase
) : ViewModel() {
    private val _reminder =
        MutableStateFlow<Resource<Reminder>>(Resource.Success(getInitialReminder()))
    var localDate: LocalDate = LocalDate.now()
    var localTime: LocalTime = LocalTime.now()
    private val snackBarChannel = Channel<SnackBarEvent>()
    val receiveChannel = snackBarChannel.receiveAsFlow()
    val reminder = _reminder.asStateFlow()

    private val _showTagDialog = MutableStateFlow(false)
    val showTagDialog = _showTagDialog.asStateFlow()

    private val _tags = MutableStateFlow(emptyList<Tag>())
    val tags = _tags.asStateFlow()
    private val createdTags = mutableListOf<String>()
    val shouldShowSaveButton = _reminder.map {
        if (it !is Resource.Success || it.data == null)
            false
        else if (it.data.title.isBlank() && it.data.description.isBlank())
            false
        else it.data.date >= System.currentTimeMillis()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    init {
        observeReminder()
        observeTags()
    }

    private fun observeTags() {
        viewModelScope.launch {
            getTagsUseCase().collectLatest {
                _tags.update { _ -> it }
            }
        }
    }

    fun updateTitle(title: String) {
        _reminder.update { Resource.Success(it.data?.copy(title = title) ?: return) }
    }

    fun updateDescription(description: String) {
        _reminder.update { Resource.Success(it.data?.copy(description = description) ?: return) }
    }

    fun updateDate() {
        val date = Date.from(localDate.atTime(localTime).atZone(ZoneId.systemDefault()).toInstant())
        _reminder.update { Resource.Success(it.data?.copy(date = date.time) ?: return) }
    }

    fun saveReminder(onSaved: (String) -> Unit): Job = viewModelScope.launch {
        val reminder = _reminder.value.data ?: return@launch
        _tags.value.filter { it.tagId in createdTags }
            .forEach { tag ->
                createTagUseCase(tag)
            }
        val result = if (reminderId.isBlank())
            createReminderUseCase(reminder)
        else
            updateReminderUseCase(reminder)

        val event = when (result) {
            is Resource.Success -> SnackBarEvent("Reminder saved successfully", "View") {
                onSaved(reminder.reminderId)
            }
            else -> SnackBarEvent("Error saving reminder") {
                saveReminder(onSaved)
            }
        }
        snackBarChannel.send(event)

    }

    @OptIn(FlowPreview::class)
    private fun observeReminder() = viewModelScope.launch {
        if (reminderId.isBlank()) return@launch
        getReminderUseCase(reminderId).debounce(200)
            .collect { r ->
                _reminder.update { _ ->
                    if (r != null)
                        Resource.Success(r)
                    else
                        Resource.Error("Reminder not found")
                }
            }

    }

    fun addTag(tag: Tag) {
        _reminder.update { Resource.Success(it.data?.copy(tags = it.data.tags + tag) ?: return) }
    }

    fun removeTag(tag: Tag) {
        _reminder.update { Resource.Success(it.data?.copy(tags = it.data.tags - tag) ?: return) }
    }

    fun createTag(tagName: String) = viewModelScope.launch {
        val tag = Tag(tagName)
        createdTags.add(tag.tagId)
        addTag(tag)
    }

    fun toggleTagDialog() {
        _showTagDialog.update { !it }
    }

    private fun getInitialReminder() = Reminder(
        reminderId = UUID.randomUUID().toString(),
        title = "",
        description = "",
        date = Date().time,
        tags = emptyList(),
        isPinned = false,
        isArchived = false,
        isDeleted = false,
        isFavorite = false,
        createdAt = Date().time
    )
}
