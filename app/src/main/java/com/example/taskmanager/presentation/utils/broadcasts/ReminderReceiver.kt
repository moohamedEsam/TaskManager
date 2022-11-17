package com.example.taskmanager.presentation.utils.broadcasts

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.taskmanager.MainActivity
import com.example.taskmanager.R
import java.util.Date

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val title = intent?.getStringExtra("title") ?: ""
        val description = intent?.getStringExtra("description") ?: ""
        val id = intent?.getStringExtra("reminderId") ?: "  "
        val viewPendingIntent = TaskStackBuilder.create(context).run {
            val deepLinkIntent = Intent(
                Intent.ACTION_VIEW,
                "https://reminders/$id".toUri(),
                context,
                MainActivity::class.java
            )
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        val notification = NotificationCompat
            .Builder(context, "reminder")
            .setSmallIcon(R.drawable.pin)
            .setContentTitle(title)
            .setContentText(description)
            .addAction(R.drawable.pin, "View", viewPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.notify(1, notification)
    }
}