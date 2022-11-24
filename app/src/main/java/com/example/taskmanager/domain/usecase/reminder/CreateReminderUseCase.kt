package com.example.taskmanager.domain.usecase.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.reminder.Reminder
import com.example.taskmanager.domain.repository.ReminderRepository
import com.example.taskmanager.presentation.utils.broadcasts.ReminderReceiver
import java.util.Date

fun interface CreateReminderUseCase : suspend (Reminder) -> Resource<Unit>

fun createReminderUseCase(reminderRepository: ReminderRepository, context: Context) =
    CreateReminderUseCase {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("reminderId", it.reminderId)
            putExtra("title", it.title)
            putExtra("description", it.description)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, it.date, pendingIntent)
        reminderRepository.addReminder(it)
    }