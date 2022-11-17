package com.example.taskmanager.presentation.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.taskmanager.presentation.di.mainModule
import com.example.taskmanager.presentation.di.noteModule
import com.example.taskmanager.presentation.di.reminderModule
import com.example.taskmanager.presentation.di.tagModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ApplicationClass)
            modules(noteModule, mainModule, tagModule, reminderModule)
        }
        val notificationChannel =
            NotificationChannel("reminder", "Reminder", NotificationManager.IMPORTANCE_HIGH)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}