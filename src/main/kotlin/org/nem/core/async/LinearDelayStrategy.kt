package org.nem.core.async

/**
 * DelayStrategy that linearly increases a delay from a minimum value
 * to a maximum value.
 *
 * Creates a new linear delay strategy.
 *
 * @param minDelay The minimum delay.
 * @param maxDelay The maximum delay.
 * @param iterations The number of iterations.
 */
class LinearDelayStrategy(private val minDelay: Int, maxDelay: Int, iterations: Int) : AbstractDelayStrategy(iterations) {

    private val delayStep: Float

    init {
        this.delayStep = (maxDelay - minDelay).toFloat() / (iterations - 1)
    }

    override fun nextInternal(iteration: Int): Int = this.minDelay + (this.delayStep * (iteration - 1)).toInt()

    companion object {

        /**
         * Creates a new linear delay strategy with an approximate back-off duration.

         * @param minDelay The minimum delay.
         * *
         * @param maxDelay The maximum delay.
         * *
         * @param duration The desired (approximate duration).
         * *
         * @return A new strategy.
         */
        fun withDuration(minDelay: Int, maxDelay: Int, duration: Int): LinearDelayStrategy {
            if (duration < minDelay + maxDelay) {
                throw IllegalArgumentException("duration must be at least minDelay + maxDelay")
            }

            val iterations = 2 * duration / (minDelay + maxDelay)
            return LinearDelayStrategy(minDelay, maxDelay, iterations)
        }
    }
}

