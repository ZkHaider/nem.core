package org.nem.core.async

import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

/**
 * Async timer options.
 */
interface AsyncTimerOptions {

    /**
     * Gets the recurring future supplier.

     * @return The recurring future supplier.
     */
    val recurringFutureSupplier: Supplier<CompletableFuture<*>>

    /**
     * Gets the initial trigger.

     * @return The initial trigger.
     */
    val initialTrigger: CompletableFuture<*>

    /**
     * Gets the delay strategy.

     * @return The delay strategy.
     */
    val delayStrategy: AbstractDelayStrategy

    /**
     * Gets the timer visitor.

     * @return The timer visitor.
     */
    val visitor: AsyncTimerVisitor
}
