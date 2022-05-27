package dev.ky3he4ik.chessproblems.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class StopwatchService : Service() {
    private val timer = Timer()
    private var startTime: Long = 0

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val time = intent?.getLongExtra(TIME_EXTRA, 0) ?: 0
        startTime = System.currentTimeMillis() - time
        timer.scheduleAtFixedRate(TimerTask(), 0, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    inner class TimerTask() : java.util.TimerTask() {
        override fun run() {
            sendBroadcast(Intent(TIMER_UPDATED).putExtra(TIME_EXTRA, System.currentTimeMillis() - startTime))
        }
    }

    companion object {
        const val TIMER_UPDATED = "timer_updated"
        const val TIME_EXTRA = "time_extra"
    }
}
