package com.example.pos2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

open class PlayMusicBroadcastReceiver : BroadcastReceiver(){
    private val TAG = "PlayMusicBroadcastReceiver"
    var music_status = ""




    override fun onReceive(context: Context, intent: Intent) {
        StringBuilder().apply {
            append("ActionL ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Log.d (TAG, log)
                Toast.makeText(context,log,Toast.LENGTH_LONG).show()
            }
            if (intent.action == ACTION_POS_PLAY){
                Intent (context,PlayMusicInBGService::class.java).also { intent -> context.startService(intent)
                    music_status = "play"
                }

            }
            else if (intent.action == ACTION_POS_STOP){
                Intent (context,PlayMusicInBGService::class.java).also { intent -> context.stopService(intent)
                    music_status = "stop"
                }
            }
        }

    }
}