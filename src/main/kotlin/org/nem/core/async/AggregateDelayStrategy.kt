package org.nem.core.async

/**
 *
 * An aggregate delay strategy.
 *
 * Creates a new aggregate delay strategy.
 * @param strategies The sub strategies.
 *
 */
class AggregateDelayStrategy(private val strategies: List<AbstractDelayStrategy>) : AbstractDelayStrategy() {

    private var strategyIndex: Int = 0

    override fun shouldStop(): Boolean {
        while (this.strategyIndex < this.strategies.size) {
            if (!this.current.shouldStop()) {
                return false
            }
            ++this.strategyIndex
        }

        return true
    }

    override fun nextInternal(iteration: Int): Int = this.current.next()

    private val current: AbstractDelayStrategy
        get() = this.strategies[this.strategyIndex]

}
