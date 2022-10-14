package com.example.taskmanager.domain.dataModels

interface Remainder {
    val id: String
    val title: String
    val description: String
    val tags: List<Tag>
    val attachments: List<Attachment>
    val date: String
    val time: String
}
