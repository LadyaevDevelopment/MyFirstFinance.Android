package ladyaev.development.myFirstFinance.core.ui.viewModel

import kotlinx.coroutines.CoroutineScope
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission

abstract class ViewModelStateAbstract<UiState : Any, StateTransmission : Any, Implementation : Any>(
    initialState: UiState,
    private val coroutineScope: CoroutineScope,
    private val transmission: Transmission.Mutable<StateTransmission, UiState>,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base()
) : Transmission.Source<StateTransmission> {

    var actual: UiState private set

    init {
        actual = initialState
    }

    final override fun read() = transmission.read()

    abstract fun map() : UiState

    abstract fun implementation(): Implementation

    fun dispatch(block: Implementation.() -> Unit) {
        dispatchers.launchMainImmediate(coroutineScope) {
            block(implementation())
            map().let {
                transmission.post(it)
                actual = it
            }
        }
    }
}