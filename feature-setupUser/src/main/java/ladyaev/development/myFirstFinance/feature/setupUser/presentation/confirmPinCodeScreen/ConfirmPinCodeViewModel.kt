package ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmPinCodeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myFirstFinance.core.common.misc.Milliseconds
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey
import ladyaev.development.myFirstFinance.core.ui.controls.progress.pinCodeProgress.DotMarkerState
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.BaseViewModel
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.feature.setupUser.business.RequireChosenCountryUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyPinCodeUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.navigation.UserStatusToScreen
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import javax.inject.Inject

open class ConfirmPinCodeViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val specifyPinCodeUseCase: SpecifyPinCodeUseCase,
    private val requireChosenCountryUseCase: RequireChosenCountryUseCase,
    dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    mutableState: Transmission.Mutable<StateTransmission, UiState>,
    mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : BaseViewModel.Stateful<
    StateTransmission,
    EffectTransmission,
    ConfirmPinCodeViewModel.UserEvent,
    ConfirmPinCodeViewModel.UiState,
    ConfirmPinCodeViewModel<StateTransmission, EffectTransmission>.ViewModelState,
    Code>(dispatchers, mutableState, mutableEffect) {

    override val viewModelState = ViewModelState()

    override fun onInitialized(firstTime: Boolean, data: Code) {
        if (firstTime) {
            viewModelState.dispatch {
                codeToConfirm = data.data
            }
        }
    }

    override fun on(event: UserEvent) {
        when (event) {
            is UserEvent.DigitalKeyPressed -> {
                handleKey(event.key)
            }
            UserEvent.ToolbarBackButtonClick -> {
                dispatchEffectSafely(UiEffect.Navigation(NavigationEvent.PopLast))
            }
            UserEvent.ErrorDialogDismiss -> {
                viewModelState.dispatch {
                    errorState = ErrorState(false)
                }
            }
        }
    }

    private fun handleKey(key: KeyboardButtonKey) {
        when (key) {
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
                    val newCode = viewModelState.enteredCode + key.string
                    viewModelState.dispatch {
                        enteredCode = newCode
                    }
                    if (newCode.length == viewModelState.codeLength) {
                        specifyPinCode()
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

    private fun specifyPinCode() {
        dispatchers.launchMain(viewModelScope) {
            viewModelState.dispatch {
                operationActive = true
            }
            val result = specifyPinCodeUseCase.process(Code(viewModelState.codeToConfirm))
            viewModelState.dispatch {
                operationActive = false
            }

            when (result) {
                is OperationResult.SpecificFailure -> {
                    when (result.error) {
                        SpecifyUserInfoError.InvalidData -> {
                            viewModelState.dispatch {
                                errorState = ErrorState(true, handleError.map(StandardError.Unknown(null)))
                            }
                        }
                    }
                }
                is OperationResult.StandardFailure -> {
                    viewModelState.dispatch {
                        errorState = ErrorState(true, handleError.map(result.error))
                    }
                }
                is OperationResult.Success -> {
                    val chosenCountry = (requireChosenCountryUseCase.process() as? OperationResult.Success)?.data
                    dispatchEffectSafely(
                        Milliseconds(500),
                        UiEffect.Navigation(
                            NavigationEvent.PopAndNavigate(
                                popToScreen = Screen.SetupUser.PhoneNumber(null),
                                inclusive = true,
                                screenToShow = UserStatusToScreen(chosenCountry).map(result.data.userStatus)))
                    )
                }
            }
        }
    }

    data class UiState(
        val codeMarkers: List<DotMarkerState> = listOf(),
        val errorState: ErrorState = ErrorState(false),
        val progressbarVisible: Boolean = false
    )

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data class DigitalKeyPressed(val key: KeyboardButtonKey) : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
    }

    inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var enteredCode: String = ""
        var codeToConfirm: String = ""
        val codeLength get() = codeToConfirm.length
        var errorState: ErrorState = ErrorState(false)
        var operationActive: Boolean = false

        private val pinCodeMarkers get() = if (enteredCode.length == codeLength) {
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

        override fun implementation() = this

        override fun map(): UiState = UiState(
            codeMarkers = pinCodeMarkers,
            errorState = errorState,
            progressbarVisible = operationActive
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        specifyPinCodeUseCase: SpecifyPinCodeUseCase,
        requireChosenCountryUseCase: RequireChosenCountryUseCase
    ) : ConfirmPinCodeViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        specifyPinCodeUseCase,
        requireChosenCountryUseCase,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}