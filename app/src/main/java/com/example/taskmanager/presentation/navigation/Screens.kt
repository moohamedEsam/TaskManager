package com.example.taskmanager.presentation.navigation

sealed class Screens(val route: String) {
    object NotesScreen : Screens("notes_screen")
    object TagsScreen : Screens("tags_screen")
    object CreateNoteScreen : Screens("create_note_screen")
    object CreateTagScreen : Screens("create_tag_screen")
    object NoteDetailsScreen : Screens("note_details_screen")
    object TagDetailsScreen : Screens("tag_details_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}