package com.example.pos2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer

class CommandCenterActivity : AppCompatActivity() {
    private val TAG = "CommandCenterActivity"
    private val CHANNEL_ID = "90200"
    private val notificationId = 1
    private val EXTRA_NOTIFICATION_ID = "notif_id"
    private var status = PlayMusicBroadcastReceiver().music_status
    private val model: MusicViewModel by viewModels()
//    private var status = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_center)

        // get reference to button
        val btnNewOrder = findViewById<Button>(R.id.neworderBtn)
        // set on-click listener
        btnNewOrder.setOnClickListener {
            val intent = Intent(this,CategoryActivity::class.java)
            startActivity(intent)

        }

        // get reference to button
        val btnAddProduct = findViewById<Button>(R.id.addproductBtn)
        // set on-click listener
        btnAddProduct.setOnClickListener {
            val intent = Intent(this,ProductCRUDActivity::class.java)
            startActivity(intent)

        }



    }


    fun onclick_play_music_in_bg_btn (view : View) {
        Log.i(TAG, "Play music in bg clicked.")
        Intent(this@CommandCenterActivity, PlayMusicInBGService::class.java).also { intent ->
            startService(intent)

        }

        //Create an explicit intent for an Activity in your app
        val intent = Intent(this, CommandCenterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val playIntent = Intent(this, PlayMusicBroadcastReceiver::class.java).apply {
            action = ACTION_POS_PLAY
            putExtra(EXTRA_NOTIFICATION_ID, notificationId)
        }


        val playPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, playIntent, 0)

        val stopIntent = Intent(this, PlayMusicBroadcastReceiver::class.java).apply {
            action = ACTION_POS_STOP
            putExtra(EXTRA_NOTIFICATION_ID, notificationId)
        }
        val stopPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, stopIntent, 0)


        //Create channel
        createNotificationChannel()
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_bg_music_stat)
            .setContentTitle(getString(R.string.music_title))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(playPendingIntent)
            .setOnlyAlertOnce(true)
            .setAutoCancel(false)
            .setContentText("Status: Playing")
            .addAction(R.drawable.ic_action_play,getString(R.string.play_str),playPendingIntent)
            .addAction(R.drawable.ic_action_stop,getString(R.string.stop_str),stopPendingIntent)

//        var notificationManager = NotificationManagerCompat.from(this)
//        notificationManager.notify(notificationId, builder.build())
//        builder.setContentText("Status: Stopped")
//        builder.setOnlyAlertOnce(true)
//        notificationManager.notify(notificationId, builder.build())

        with(NotificationManagerCompat.from(this)) {
            //notification id is unique int for each notification that you must define
            //First time
            builder.setContentText("Status: Playing")
            notify(notificationId, builder.build())


//            //Second time
//            builder.setContentText("Status: Stopped")
//            notify(notificationId, builder.build())
        }



    }



    fun onclick_stop_music_in_bg_btn (view : View){
        Log.i(TAG,"Play music in bg clicked.")
        Intent(this@CommandCenterActivity,PlayMusicInBGService::class.java).also { intent ->
            stopService(intent)
        }

        //Create an explicit intent for an Activity in your app
        val intent = Intent(this, CommandCenterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val playIntent = Intent(this, PlayMusicBroadcastReceiver::class.java).apply {
            action = ACTION_POS_PLAY
            putExtra(EXTRA_NOTIFICATION_ID, notificationId)
        }

        val playPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, playIntent, 0)

        val stopIntent = Intent(this, PlayMusicBroadcastReceiver::class.java).apply {
            action = ACTION_POS_STOP
            putExtra(EXTRA_NOTIFICATION_ID, notificationId)

        }
        val stopPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, stopIntent, 0)


        //Create channel
        createNotificationChannel()
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_bg_music_stat)
            .setContentTitle("Stamford POS BG Music Status")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(playPendingIntent)
            .setAutoCancel(false)
            .setContentText("Status = Stopped")
            .addAction(R.drawable.ic_action_play,getString(R.string.play_str),playPendingIntent)
            .addAction(R.drawable.ic_action_stop,getString(R.string.stop_str),stopPendingIntent)


        with(NotificationManagerCompat.from(this)) {
            //notification id is unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel(){
        //Create the NotificationChannel , but only on API26+ because the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Play BG Status Channel"
            val descriptionText = "A notification to show play background music status"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }



            //Register the channel with the system
            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}