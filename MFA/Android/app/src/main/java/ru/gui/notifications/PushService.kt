package ru.gui.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.gui.PushCode
import ru.gui.R

class PushService: FirebaseMessagingService() {

    private val TAG="Mylog"
    private val CHANNEL_ID= "channel_id"
    private val textTitle :String ="MARALAYSSECURITY"
    private val notification_id =101


    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")

        }
        createNotificationChannel();
        sendNotification(remoteMessage);

        /*NotificationManagerCompat.from(getApplicationContext()).notify(123,  NotificationCompat.Builder(this)
            .setContentTitle(remoteMessage.getData().get("title"))
            .setContentText(remoteMessage.getData().get("body"))
            .setSmallIcon(R.mipmap.ic_launcher)
            .build());*/

    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(application.applicationContext , PushCode::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        //intent.putExtra("messageText","remoteMessage.notification?.body.toString()")
        val pendingIntent: PendingIntent = PendingIntent.getActivity(application.applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder= NotificationCompat.Builder( application.applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_lock_24)
            .setContentTitle("Новая угроза")
            .setContentText(remoteMessage.notification?.body.toString())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(remoteMessage.notification?.body.toString()))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)){
            notify(notification_id, builder.build())
        }

    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}