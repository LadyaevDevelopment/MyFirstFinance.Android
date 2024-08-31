package ladyaev.development.myFirstFinance.core.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.misc.Milliseconds
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission

abstract class BaseViewModel<EffectTransmission : Any, UserEvent : Any, TInputData : Any>(
    protected val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    protected val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel() {

    val effect: EffectTransmission get() = mutableEffect.read()

    private lateinit var inputData: TInputData

    @Synchronized
    fun initialize(data: TInputData) {
        if (!this::inputData.isInitialized || inputData != data) {
            onInitialized(!this::inputData.isInitialized, data)
            inputData = data
        }
    }

    protected open fun onInitialized(firstTime: Boolean, data: TInputData) = run {}

    open fun on(event: UserEvent) = run {}

    protected fun doOnHideKeyboard(block: () -> Unit) {
        dispatchers.launchMain(viewModelScope) {
            mutableEffect.post(UiEffect.HideKeyboard)
            delay(300)
            block()
        }
    }

    protected fun dispatchEffectSafely(effect: UiEffect) {
        dispatchers.launchMain(viewModelScope) {
            mutableEffect.post(effect)
        }
    }

    protected fun dispatchEffectSafely(delay: Milliseconds, effect: UiEffect) {
        dispatchers.launchMain(viewModelScope) {
            delay(delay.data)
            mutableEffect.post(effect)
        }
    }

    abstract class Stateful<
        StateTransmission : Any,
        EffectTransmission : Any,
        UserEvent : Any,
        UiState : Any,
        TViewModelState : ViewModelStateAbstract<UiState, StateTransmission, *>,
        TInputData : Any>(
        dispatchers: ManageDispatchers = ManageDispatchers.Base(),
        protected val mutableState: Transmission.Mutable<StateTransmission, UiState>,
        mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
    ) : BaseViewModel<EffectTransmission, UserEvent, TInputData>(dispatchers, mutableEffect) {

        protected abstract val viewModelState : TViewModelState

        val state: StateTransmission get() = mutableState.read()
    }
}