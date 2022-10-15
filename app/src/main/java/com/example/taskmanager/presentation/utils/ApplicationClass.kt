package com.example.taskmanager.presentation.utils

import android.app.Application
import com.example.taskmanager.presentation.koin.mainModule
import com.example.taskmanager.presentation.koin.noteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ApplicationClass)
            modules(noteModule, mainModule)
        }
    }
}