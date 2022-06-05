package com.example.stopwatchdemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.security.Provider
import java.sql.Time
import java.util.*
import kotlin.concurrent.timer

class StopWatchService : Service() {
    override fun onBind(p0: Intent?): IBinder? =null
     private val timer = Timer()
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(CURRENT_TIME,0.0)
        timer.scheduleAtFixedRate(StopwatchTimertask(time),0,1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }
    companion object{
        const val CURRENT_TIME ="current_time"
        const val UPDATE_TIME ="update_time"
    }

    private inner class StopwatchTimertask(private var time:Double):TimerTask(){
        override fun run() {
            val intent = Intent(UPDATE_TIME)
            time++
            intent.putExtra(CURRENT_TIME,time)
            sendBroadcast(intent)
        }


    }

}