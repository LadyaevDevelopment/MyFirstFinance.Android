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

    fun launchBackground(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job

    suspend fun switchToMain(block: suspend CoroutineScope. () -> Unit)

    abstract class Abstract(
        private val main: CoroutineDispatcher,
        private val mainImmediate: CoroutineDispatcher,
        private val background: CoroutineDispatcher,
    ) : ManageDispatchers {

        override fun launchMain(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ): Job = scope.launch(main, block = block)

        override fun launchMainImmediate(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ): Job = scope.launch(mainImmediate, block = block)

        override fun launchBackground(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ): Job = scope.launch(background, block = block)

        override suspend fun switchToMain(block: suspend CoroutineScope. () -> Unit) = withContext(main, block)
    }

    class Base @Inject constructor() : Abstract(Dispatchers.Main, Dispatchers.Main.immediate, Dispatchers.IO)
}