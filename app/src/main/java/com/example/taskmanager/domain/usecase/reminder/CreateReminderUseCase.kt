package com.example.taskmanager.domain.usecase.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.taskmanager.domain.models.reminder.Reminder
import com.example.taskmanager.domain.repository.ReminderRepository
import com.example.taskmanager.presentation.utils.broadcasts.ReminderReceiver

fun interface CreateReminderUseCase : suspend (Reminder) -> Unit

fun createReminderUseCase(reminderRepository: ReminderRepository, context: Context) = CreateReminderUseCase {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, it.date, pendingIntent)
        reminderRepository.addReminder(it)
    }