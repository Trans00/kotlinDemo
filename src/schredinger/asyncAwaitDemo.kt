package schredinger

import java.util.concurrent.*
import java.util.function.Supplier
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val executor = Executors.newSingleThreadExecutor({ thread(isDaemon = true,start = false,name = "myThread") { it.run() }})
    val future = asyncOnExecutor<Unit>(executor) {
//    val future = async<Unit> {
        doWork(1)
        await({doWork(10)})
        doWork(1)
    }
    future.get()
}

fun <T> async(
        continuationWrapper: ContinuationWrapper? = null,
        coroutine c: FutureController<T>.() -> Continuation<Unit>
): CompletableFuture<T> {
    val controller = FutureController<T>(continuationWrapper)
    c(controller).resume(Unit)
    return controller.future
}

typealias ContinuationWrapper = (() -> Unit) -> Unit

@AllowSuspendExtensions
class FutureController<T>(
        private val continuationWrapper: ContinuationWrapper?
) {
    val future = CompletableFuture<T>()

    suspend fun <V> await(func: ()->V, machine: Continuation<V>) {
        val f: CompletableFuture<V> = CompletableFuture.supplyAsync(func)
        f.whenComplete { value, throwable ->
            wrapContinuationIfNeeded {
                if (throwable == null)
                    machine.resume(value)
                else
                    machine.resumeWithException(throwable)
            }
        }
    }

    private fun wrapContinuationIfNeeded(block: () -> Unit) {
        continuationWrapper?.invoke(block) ?: block()
    }

    operator fun handleResult(value: T, c: Continuation<Nothing>) {
        future.complete(value)
    }

    operator fun handleException(t: Throwable, c: Continuation<Nothing>) {
        future.completeExceptionally(t)
    }
}

fun <T> asyncOnExecutor(
        executor: Executor,
        continuationWrapper: ContinuationWrapper? = null,
        coroutine c: ExecutorFutureController<T>.() -> Continuation<Unit>
): CompletableFuture<T> {
    val controller = ExecutorFutureController<T>(executor,continuationWrapper)
    c(controller).resume(Unit)
    return controller.future
}

@AllowSuspendExtensions
class ExecutorFutureController<T>(
        private val executor: Executor,
        private val continuationWrapper: ContinuationWrapper?
) {
    val future = CompletableFuture<T>()

    suspend fun <V> await(func: ()->V, machine: Continuation<V>) {
        val f: CompletableFuture<V> = CompletableFuture.supplyAsync(Supplier(func), executor)
        f.whenComplete { value, throwable ->
            wrapContinuationIfNeeded {
                if (throwable == null)
                    machine.resume(value)
                else
                    machine.resumeWithException(throwable)
            }
        }
    }

    private fun wrapContinuationIfNeeded(block: () -> Unit) {
        continuationWrapper?.invoke(block) ?: block()
    }

    operator fun handleResult(value: T, c: Continuation<Nothing>) {
        future.complete(value)
    }

    operator fun handleException(t: Throwable, c: Continuation<Nothing>) {
        future.completeExceptionally(t)
    }
}