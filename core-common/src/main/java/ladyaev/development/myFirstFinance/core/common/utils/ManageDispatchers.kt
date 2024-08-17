package ladyaev.development.myFirstFinance.core.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ManageDispatchers {

    fun launchMain(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job

    fun launchMainImmediate(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job

    fun launchIO(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job

    suspend fun<T> withMain(block: suspend CoroutineScope.() -> T): T

    suspend fun<T> withMainImmediate(block: suspend CoroutineScope.() -> T): T

    suspend fun<T> withIO(block: suspend CoroutineScope.() -> T): T

    suspend fun switchToMain(block: suspend CoroutineScope.() -> Unit)

    suspend fun switchToMainImmediate(block: suspend CoroutineScope.() -> Unit)

    suspend fun switchToIO(block: suspend CoroutineScope.() -> Unit)

    abstract class Abstract(
        private val main: CoroutineDispatcher,
        private val mainImmediate: CoroutineDispatcher,
        private val io: CoroutineDispatcher,
    ) : ManageDispatchers {

        override fun launchMain(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ): Job = scope.launch(main, block = block)

        override fun launchMainImmediate(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ): Job = scope.launch(mainImmediate, block = block)

        override fun launchIO(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ): Job = scope.launch(io, block = block)

        override suspend fun<T> withMain(block: suspend CoroutineScope.() -> T): T = withContext(main, block)

        override suspend fun<T> withMainImmediate(block: suspend CoroutineScope.() -> T): T = withContext(mainImmediate, block)

        override suspend fun<T> withIO(block: suspend CoroutineScope.() -> T): T = withContext(io, block)

        override suspend fun switchToMain(block: suspend CoroutineScope.() -> Unit) = withContext(main, block)

        override suspend fun switchToMainImmediate(block: suspend CoroutineScope.() -> Unit) = withContext(mainImmediate, block)

        override suspend fun switchToIO(block: suspend CoroutineScope.() -> Unit) = withContext(io, block)
    }

    class Base @Inject constructor() : Abstract(Dispatchers.Main, Dispatchers.Main.immediate, Dispatchers.IO)
}