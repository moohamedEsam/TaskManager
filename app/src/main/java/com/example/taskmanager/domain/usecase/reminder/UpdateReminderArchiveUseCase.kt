package com.example.taskmanager.domain.usecase.reminder

import com.example.taskmanager.domain.models.Resource

fun interface UpdateReminderArchiveUseCase : suspend (String, Boolean) -> Resource<Unit>
