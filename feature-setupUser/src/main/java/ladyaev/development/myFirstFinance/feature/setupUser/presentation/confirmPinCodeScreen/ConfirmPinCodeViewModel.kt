package ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmPinCodeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.interfaces.Strategy
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey
import ladyaev.development.myFirstFinance.core.ui.controls.progress.pinCodeProgress.DotMarkerState
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import javax.inject.Inject

open class ConfirmPinCodeViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val setupUserRepository: SetupUserRepository,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel() {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    fun initialize(firstTime: Boolean, code: String) {
        if (firstTime) {
            viewModelState.dispatch {
                codeToConfirm = code
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
                        if (viewModelState.enteredCode.length < viewModelState.codeLength) {
                            val newCode = viewModelState.enteredCode + event.key.string
                            viewModelState.dispatch {
                                enteredCode = newCode
                            }
                            if (newCode.length == viewModelState.codeLength) {
                                dispatchers.launchMain(viewModelScope) {
                                    delay(500)
                                    mutableEffect.post(
                                        UiEffect.Navigation(
                                            NavigationEvent.PopAndNavigate(
                                                popToScreen = Screen.SetupUser.PhoneNumber(null),
                                                inclusive = true,
                                                screenToShow = Screen.SetupUser.CompleteRegistration())))
                                }
                            }
                        }
                    }
                    KeyboardButtonKey.Delete -> {
                        if (viewModelState.enteredCode.isNotEmpty()) {
                            viewModelState.dispatch {
                                enteredCode = enteredCode.substring(0, enteredCode.lastIndex)
                            }
                        }
                    }
                    KeyboardButtonKey.Fake -> {}
                }
            }
            UserEvent.ToolbarBackButtonClick -> {
                mutableEffect.post(UiEffect.Navigation(NavigationEvent.PopLast))
            }
            UserEvent.ErrorDialogDismiss -> {
                viewModelState.dispatch {
                    errorState = ErrorState(false)
                }
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
        data object ErrorDialogDismiss : UserEvent()
    }

    private inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var enteredCode: String = ""
        var codeToConfirm: String = ""
        val codeLength get() = codeToConfirm.length
        var errorState: ErrorState = ErrorState(false)

        override fun implementation() = this

        override fun map(): UiState = UiState(
            codeMarkers = PinCodeMarkersStrategy().resolved,
            errorState = errorState
        )

        private inner class PinCodeMarkersStrategy : Strategy<List<DotMarkerState>> {
            override val resolved: List<DotMarkerState> = if (enteredCode.length == codeLength) {
                if (enteredCode == codeToConfirm) {
                    List(codeLength) { DotMarkerState.Success }
                } else {
                    List(codeLength) { DotMarkerState.Error }
                }
            } else {
                List(codeLength) { index ->
                    if (index < enteredCode.length) DotMarkerState.Active else DotMarkerState.Default
                }
            }
        }
    }

    class Base @Inject constructor(
        handleError: HandleError,
        setupUserRepository: ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository,
    ) : ConfirmPinCodeViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        setupUserRepository,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}