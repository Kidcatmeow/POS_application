package com.example.pos2
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.widget.Toast

class PlayMusicInBGService : Service() {

    //A boolean variable to let us know if the music is playing or not
    //true : playing , false : not playing
    private var playStatus : Boolean = false

    //MediaPlayer Instance
    private var mPlayer : MediaPlayer? = null

    //Looper is a worker that keeps a thread alive
    //It loops through MessageQueue and sends messages to the corresponding handler to process
    private var serviceLooper : Looper? = null

    //A handler allows you to send and process Message and Runnable objects associated with a thread's MessageQueue.
    private var serviceHandler : ServiceHandler? = null

    //Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper){

        override fun handleMessage(msg: Message) {
            //Normally we would do some work here like download a file or play a music

            try {
                Toast.makeText(this@PlayMusicInBGService, "Service created.", Toast.LENGTH_SHORT).show()
                    if (!playStatus){
                        mPlayer = MediaPlayer.create(this@PlayMusicInBGService,R.raw.sample)
                        mPlayer?.start()
                        playStatus = true
                    }
            } catch (e : InterruptedException) { //Restore interrupt status.
                Thread.currentThread().interrupt()
            }
            //Stop the service using the startIdmso that we don't stop the service in the middle of handling another job
            // stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        //Start up the thread running the service
        //Noted: we create a separate thread because the service normally runs in the process's main thread which we don't want to block
        //We also make it back priority so CPU-intensive work will not disrupt our UI
        HandlerThread("ServiceStartArguments",Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()
            //Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this,"service starting",Toast.LENGTH_SHORT).show()

        //For each start request,send a message to start a job and deliver the start ID
        //so we know hich request we are stopping wen we finish the job
        serviceHandler?.obtainMessage()?.also { msg->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }

        //if we get killed,restart after returning from here
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
       //we don't provide binding ,so return null
        return null
    }

    override fun onDestroy() {
        mPlayer?.stop()
        playStatus = false
        stopSelf()
        Toast.makeText(this,"service done",Toast.LENGTH_SHORT).show()
    }


}