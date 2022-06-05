package com.example.stopwatchdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stopwatchdemo.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
private lateinit var binding:ActivityMainBinding
private var isStart = false
    private lateinit var serviceIntent:Intent
    private var time = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startbtn.setOnClickListener {
            startOrStop()

        }
        binding.resetbtn.setOnClickListener {
            reset()

        }
        serviceIntent = Intent(applicationContext,StopWatchService::class.java)
        registerReceiver(UpdateTime, IntentFilter(StopWatchService.UPDATE_TIME))
    }
    private fun startOrStop(){
        if(isStart)
            stop()
        else{
            start()
        }
    }

    private fun start(){
        serviceIntent.putExtra(StopWatchService.CURRENT_TIME,time)
        startService(serviceIntent)
         binding.startbtn.text = "Stop"
        isStart=true

    }
    private fun stop(){
        stopService(serviceIntent)
        binding.startbtn.text="Start"
        isStart=false
    }
    private fun reset(){
        stop()
        time=0.0
        binding.tvwatch.text=getformatedTime(time)

    }

    private val UpdateTime :BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(StopWatchService.CURRENT_TIME,0.0)
            binding.tvwatch.text=getformatedTime(time)
        }

    }

    private fun getformatedTime(time: Double):String {
        val timeInt = time.roundToInt()
        val hours = timeInt % 8600 /3600
        val minutes = timeInt % 8600 % 3600 /60
        val seconds = timeInt % 8600 % 3600 % 60
        return String.format("%02d:%02d:%02d",hours,minutes,seconds)

    }


}