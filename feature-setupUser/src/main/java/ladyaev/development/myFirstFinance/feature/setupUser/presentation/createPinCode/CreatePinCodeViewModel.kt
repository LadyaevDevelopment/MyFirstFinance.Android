package ladyaev.development.myFirstFinance.feature.setupUser.presentation.createPinCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey
import ladyaev.development.myFirstFinance.core.ui.controls.progress.pinCodeProgress.DotMarkerState
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmPinCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import javax.inject.Inject

open class CreatePinCodeViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val setupUserRepository: SetupUserRepository,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel() {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    fun initialize(firstTime: Boolean) {
        if (firstTime) {
            viewModelState.dispatch {
                codeLength = CODE_LENGTH
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
                                dispatchers.launchMain(viewModelScope) {
                                    delay(500)
                                    mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.ConfirmPinCode(
                                        ConfirmPinCodeScreenArguments(viewModelState.code)
                                    ))))
                                }
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
                mutableEffect.post(UiEffect.Navigation(NavigationEvent.PopLast))
            }
        }
    }

    sealed class UiEffect {
        data class Navigation(val navigationEvent: NavigationEvent) : UiEffect()
    }

    data class UiState(
        val codeMarkers: List<DotMarkerState> = listOf(),
        val errorState: ErrorState = ErrorState(false)
    )

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data class DigitalKeyPressed(val key: KeyboardButtonKey) : UserEvent()
    }

    private inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
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
        setupUserRepository: ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository,
    ) : CreatePinCodeViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        setupUserRepository,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )

    companion object {
        private const val CODE_LENGTH = 4
    }
}