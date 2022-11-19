package com.example.taskmanager.domain.usecase.reminder

import com.example.taskmanager.domain.models.Resource

fun interface UpdateReminderDeleteUseCase : suspend (String, Boolean) -> Resource<Unit>
