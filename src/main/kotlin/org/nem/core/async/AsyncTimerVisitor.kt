package org.nem.core.async

/**
 * A visitor that is called by the AsyncTimer.
 */
interface AsyncTimerVisitor {

    /**
     * Called to indicate that a user operation is starting.
     */
    fun notifyOperationStart()

    /**
     * Called to indicate that a user operation has completed (successfully).
     */
    fun notifyOperationComplete()

    /**
     * Called to indicate that a user operation has completed (exceptionally).

     * @param ex The exception.
     */
    fun notifyOperationCompleteExceptionally(ex: Throwable)

    /**
     * Called to indicate that the timer will delay for the specified interval before starting
     * the next operation.

     * @param delay The delay in milliseconds.
     */
    fun notifyDelay(delay: Int)

    /**
     * Called to indicate that the timer has stopped.
     */
    fun notifyStop()

    /**
     * Gets the name of the timer

     * @return The name.
     */
    val timerName: String
}

