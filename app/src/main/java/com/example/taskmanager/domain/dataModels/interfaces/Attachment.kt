package com.example.taskmanager.domain.dataModels.interfaces

import kotlinx.serialization.Serializable

@Serializable
sealed class Attachment {
    @Serializable
    class Photo(val path: String) : Attachment()

    @Serializable
    class Video(val path: String) : Attachment()

    @Serializable
    class Audio(val path: String) : Attachment()

    @Serializable
    class File(val path: String) : Attachment()
}
