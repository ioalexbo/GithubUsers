package com.alexlepadatu.meisterlabs.util

import android.os.Handler

class DelayedActionHandler<T>(private val delay: Long, action: (t: T?) -> Unit) {
    private var t: T? = null

    private val handler = Handler()
    private var runnable: Runnable = Runnable {
        action(t)
    }

    fun setItem(t: T?) {
        this.t = t

        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, delay)
    }
}