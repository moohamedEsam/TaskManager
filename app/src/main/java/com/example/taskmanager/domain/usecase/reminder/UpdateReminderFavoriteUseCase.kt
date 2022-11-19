package com.example.taskmanager.domain.usecase.reminder

import com.example.taskmanager.domain.models.Resource

fun interface UpdateReminderFavoriteUseCase : suspend (String, Boolean) -> Resource<Unit>
