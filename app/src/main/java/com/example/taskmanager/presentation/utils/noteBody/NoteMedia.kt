package com.example.taskmanager.presentation.utils.noteBody

sealed interface NoteMedia : NoteBody {
    var uriString: String
}
