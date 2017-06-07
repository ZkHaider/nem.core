package org.nem.core.async

import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Static class containing methods for creating a delayed future.
 */
object SleepFuture {

    private val TIMER = Timer(true)

    /**
     * Creates a new future that fires at the specified time in the future.

     * @param delay The delay (in milliseconds).
     * *
     * @param  Any type.
     * *
     * @return The future.
     */
    fun <T> create(delay: Int): CompletableFuture<T> {
        val future = CompletableFuture<T>()
        TIMER.schedule(object : TimerTask() {
            override fun run() {
                future.complete(null)
            }
        }, delay.toLong())
        return future
    }
}
