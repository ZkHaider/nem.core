package org.nem.core.async

abstract class AbstractDelayStrategy {

    private val maxDelays: Int?
    private var numDelays: Int = 0

    /**
     * Creates a new abstract delay strategy.
     */
    protected constructor() {
        this.maxDelays = null
    }

    /**
     * Creates a new finite abstract delay strategy.

     * @param maxDelays The maximum number of times next is allowed to be called.
     */
    protected constructor(maxDelays: Int) {
        this.maxDelays = maxDelays
    }

    /**
     * Gets a value indicating whether or not all delays are exhausted
     * (and the recurring operation should stop).

     * @return true if the recurring operation should be stopped.
     */
    open fun shouldStop(): Boolean {
        return null != this.maxDelays && this.maxDelays <= this.numDelays
    }

    /**
     * Gets the next delay (in milliseconds).

     * @return The next delay.
     */
    operator fun next(): Int {
        if (this.shouldStop()) {
            throw IllegalStateException("the delay strategy is exhausted")
        }

        return this.nextInternal(++this.numDelays)
    }

    /**
     * Gets the next delay (in milliseconds).

     * @param iteration The 1-based call count.
     * *
     * @return The next delay.
     */
    protected abstract fun nextInternal(iteration: Int): Int
}