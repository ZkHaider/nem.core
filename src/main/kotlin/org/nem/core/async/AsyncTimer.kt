package org.nem.core.async

import java.io.Closeable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Supplier
import java.util.logging.Logger

open class AsyncTimer(final val options: AsyncTimerOptions) : Closeable {

    private val LOGGER = Logger.getLogger(NemAsyncTimerVisitor::class.java.name)

    private val recurringFutureSupplier: Supplier<CompletableFuture<*>> = options.recurringFutureSupplier
    private val delay: AbstractDelayStrategy = options.delayStrategy
    private var future: CompletableFuture<*> = options.initialTrigger.thenCompose({ v -> this.getNextChainLink() })
    private val visitor: AsyncTimerVisitor = options.visitor

    val isStopped = AtomicBoolean()
        get() = field
    val firstRecurrenceFuture = CompletableFuture<Any>()
        get() = field

    private fun chain(delay: Int) {
        this.visitor.notifyDelay(delay)
        val oldFuture = this.future
        this.future = SleepFuture.create<Any>(delay).thenCompose({ v -> this.getNextChainLink() })
        oldFuture.complete(null)
    }

    override fun close() = this.isStopped.set(true)

    private fun getNextChainLink(): CompletableFuture<Void> {
        if (this.isStopped.get() || this.delay.shouldStop()) {
            this.visitor.notifyStop()
            val terminatingFuture = CompletableFuture<Void>()
            terminatingFuture.complete(null)
            return terminatingFuture
        }

        try {
            this.visitor.notifyOperationStart()
			return this.recurringFutureSupplier.get()
                    .thenAccept({ v -> this.visitor.notifyOperationComplete() })
                    .exceptionally({
                        this.visitor.notifyOperationCompleteExceptionally(it)
                        null
                    })
                    .thenAccept({ v ->
                        this.firstRecurrenceFuture.complete(null)
                        this.chain(this.delay.next())
                    })
		} catch (e: Exception) {
			LOGGER.warning(String.format("Timer %s raised exception during start: %s",
					this.visitor.timerName,
					e.toString()));
			this.chain(this.delay.next());
			return CompletableFuture.completedFuture(null);
		}
    }

}