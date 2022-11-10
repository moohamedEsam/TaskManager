package com.example.taskmanager.presentation.utils.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("ReminderReceiver", "onReceive: ${intent?.action}")
    }
}