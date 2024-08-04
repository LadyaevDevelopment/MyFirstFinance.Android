package ladyaev.development.myFirstFinance.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.util.concurrent.atomic.AtomicReference

open class Cache<TData : Any>(
    private val requestData: suspend () -> TData,
    private val dataValid: (data: TData) -> Boolean
) {
    private var cachedData: AtomicReference<TData?> = AtomicReference(null)

    private var job: AtomicReference<Deferred<TData>?> = AtomicReference(null)

    suspend fun data(scope: CoroutineScope): TData {
        val data = cachedData.get()
        if (data != null) {
            return data
        }

        return job.updateAndGet { currentJob ->
            currentJob?.takeIf { it.isActive } ?: scope.async {
                requestData().also { data ->
                    if (dataValid(data)) {
                        cachedData.set(data)
                    } else {
                        cachedData.set(null)
                    }
                    job.set(null)
                }
            }
        }!!.await()
    }
}