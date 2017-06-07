package org.nem.core.async

/**
 * DelayStrategy that produces a uniform delay.
 */
class UniformDelayStrategy: AbstractDelayStrategy {

    private val delay: Int

    /**
     * Creates a new infinite uniform delay strategy.

     * @param delay The delay interval.
     */
    constructor(delay: Int) {
        this.delay = delay
    }

    /**
     * Creates a new finite uniform delay strategy.

     * @param delay The delay interval.
     * *
     * @param maxDelays The maximum number of delays.
     */
    constructor(delay: Int, maxDelays: Int) : super(maxDelays) {
        this.delay = delay
    }

    override fun nextInternal(iteration: Int): Int {
        return this.delay
    }
}

