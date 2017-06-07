package org.nem.core.async

import java.util.function.Supplier

/**
 * DelayStrategy that produces variable delays produced by an external supplier.
 */
class VariableDelayStrategy : AbstractDelayStrategy {

    private val delaySupplier: Supplier<Int>

    /**
     * Creates a new infinite variable delay strategy.

     * @param delaySupplier The delay supplier.
     */
    constructor(delaySupplier: Supplier<Int>) {
        this.delaySupplier = delaySupplier
    }

    /**
     * Creates a new finite variable delay strategy.

     * @param delaySupplier The delay supplier.
     * *
     * @param maxDelays The maximum number of delays.
     */
    constructor(delaySupplier: Supplier<Int>, maxDelays: Int) : super(maxDelays) {
        this.delaySupplier = delaySupplier
    }

    override fun nextInternal(iteration: Int): Int = this.delaySupplier.get()
}

