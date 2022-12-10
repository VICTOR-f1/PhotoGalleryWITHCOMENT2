package com.bignerdranch.android.photogallery

import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat

private const val TAG = "NotificationReceiver"
//Широковещательный приемник — компонент, который
//получает интенты, как и служба или activity. При получении
//интента экземпляром NotificationReceiver будет вызвана
//его функция onReceive(...).
//данный класс  отправляет уведомление пользователю.
class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "Received result: $resultCode")

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val requestCode = intent.getIntExtra(PollWorker.REQUEST_CODE, 0)
        val notification: Notification? = intent.getParcelableExtra(PollWorker.NOTIFICATION)
        val notificationManager = NotificationManagerCompat.from(context)
        notification?.let { notificationManager.notify(requestCode, it) }
    }
}