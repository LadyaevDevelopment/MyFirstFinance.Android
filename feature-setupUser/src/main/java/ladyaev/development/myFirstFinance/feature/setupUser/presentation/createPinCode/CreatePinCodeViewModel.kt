package ladyaev.development.myFirstFinance.feature.setupUser.presentation.createPinCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ladyaev.development.myFirstFinance.core.common.misc.Length
import ladyaev.development.myFirstFinance.core.common.misc.Milliseconds
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey
import ladyaev.development.myFirstFinance.core.ui.controls.progress.pinCodeProgress.DotMarkerState
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmPinCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.BaseViewModel
import ladyaev.development.myFirstFinance.core.ui.viewModel.state.ViewModelStateAbstract
import javax.inject.Inject

open class CreatePinCodeViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    mutableState: Transmission.Mutable<StateTransmission, UiState>,
    mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : BaseViewModel.Stateful<
    StateTransmission,
    EffectTransmission,
    CreatePinCodeViewModel.UiState,
    CreatePinCodeViewModel<StateTransmission, EffectTransmission>.ViewModelState,
    Length>(dispatchers, mutableState, mutableEffect) {

    override val viewModelState = ViewModelState()

    override fun initialize(firstTime: Boolean, data: Length) {
        if (firstTime) {
            viewModelState.dispatch {
                codeLength = data.data
            }
        }
    }

    fun on(event: UserEvent) {
        when (event) {
            is UserEvent.DigitalKeyPressed -> {
                when (event.key) {
                    KeyboardButtonKey.Key0,
                    KeyboardButtonKey.Key1,
                    KeyboardButtonKey.Key2,
                    KeyboardButtonKey.Key3,
                    KeyboardButtonKey.Key4,
                    KeyboardButtonKey.Key5,
                    KeyboardButtonKey.Key6,
                    KeyboardButtonKey.Key7,
                    KeyboardButtonKey.Key8,
                    KeyboardButtonKey.Key9 -> {
                        if (viewModelState.code.length < viewModelState.codeLength) {
                            val newCode = viewModelState.code + event.key.string
                            viewModelState.dispatch {
                                code = newCode
                            }
                            if (newCode.length == viewModelState.codeLength) {
                                dispatchEffectSafely(
                                    Milliseconds(500),
                                    UiEffect.Navigation(
                                        NavigationEvent.Navigate(Screen.SetupUser.ConfirmPinCode(
                                            ConfirmPinCodeScreenArguments(viewModelState.code))))
                                )
                            }
                        }
                    }
                    KeyboardButtonKey.Delete -> {
                        if (viewModelState.code.isNotEmpty()) {
                            viewModelState.dispatch {
                                code = code.substring(0, code.lastIndex)
                            }
                        }
                    }
                    KeyboardButtonKey.Fake -> {}
                }
            }
            UserEvent.ToolbarBackButtonClick -> {
                dispatchEffectSafely(UiEffect.Navigation(NavigationEvent.PopLast))
            }
        }
    }

    data class UiState(
        val codeMarkers: List<DotMarkerState> = listOf(),
        val errorState: ErrorState = ErrorState(false)
    )

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data class DigitalKeyPressed(val key: KeyboardButtonKey) : UserEvent()
    }

    inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var codeLength: Int = 0
        var code: String = ""

        override fun implementation() = this

        override fun map(): UiState = UiState(
            codeMarkers = List(codeLength) { index ->
                if (index < code.length) DotMarkerState.Active else DotMarkerState.Default
            }
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
    ) : CreatePinCodeViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}