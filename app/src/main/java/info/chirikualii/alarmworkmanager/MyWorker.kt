package info.chirikualii.alarmworkmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context,workers: WorkerParameters) : Worker(context,workers) {

    override fun doWork(): Result {
        displayNotification(task = "OneTime", desc ="This is one time request")
        return Result.success()
    }

    private fun displayNotification(task: String, desc: String) {
        val notifManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("MyAlarm","MyAlarm",NotificationManager.IMPORTANCE_DEFAULT)
            notifManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext,"MyAlarm")
            .setContentTitle(task)
            .setContentText(desc)
            .setSmallIcon(R.mipmap.ic_launcher)

        notifManager.notify(1,builder.build())
    }
}