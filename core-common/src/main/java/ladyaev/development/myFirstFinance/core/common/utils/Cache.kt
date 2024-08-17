package ladyaev.development.myFirstFinance.core.common.utils

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.atomic.AtomicReference

open class Cache<TData : Any>(
    private val requestData: suspend () -> TData,
    private val dataValid: (data: TData) -> Boolean
) {
    private var cachedData: AtomicReference<TData?> = AtomicReference(null)

    private var job: AtomicReference<Deferred<TData>?> = AtomicReference(null)

    suspend fun data(): TData {
        val data = cachedData.get()
        if (data != null) {
            return data
        }

        return coroutineScope {
            job.updateAndGet { currentJob ->
                currentJob?.takeIf { it.isActive } ?: async {
                    requestData().also { data ->
                        if (dataValid(data)) {
                            cachedData.set(data)
                        } else {
                            cachedData.set(null)
                        }
                        job.set(null)
                    }
                }
            }
        }!!.await()
    }
}