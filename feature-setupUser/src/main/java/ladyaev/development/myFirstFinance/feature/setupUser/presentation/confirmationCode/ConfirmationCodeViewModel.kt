package ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmationCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.utils.ManageResources
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myFirstFinance.core.common.misc.Seconds
import ladyaev.development.myFirstFinance.core.common.interfaces.Strategy
import ladyaev.development.myFirstFinance.core.di.timer
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.input.inputCell.InputCellData
import ladyaev.development.myFirstFinance.core.ui.controls.input.inputCell.InputCellState
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError
import javax.inject.Inject

open class ConfirmationCodeViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val manageResources: ManageResources,
    private val setupUserRepository: SetupUserRepository,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel() {

    private val viewModelState = ViewModelState()

    private var timerJob: Job? = null

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    fun initialize(firstTime: Boolean, phoneNumber: PhoneNumber) {
        if (firstTime) {
            viewModelState.dispatch {
                this.phoneNumber = phoneNumber
            }
            requireConfirmationCode(phoneNumber)
        }
    }

    private fun startTimer(seconds: Int) {
        timerJob = viewModelScope.launch {
            timer(seconds).collect {
                viewModelState.dispatch {
                    remainingTime = Seconds(it)
                }
            }
        }
    }

    private fun requireConfirmationCode(phoneNumber: PhoneNumber) {
        if (viewModelState.requireCodeOperationActive || !viewModelState.actual.requireCodeBtnEnabled) {
            return
        }
        dispatchers.launchBackground(viewModelScope) {
            viewModelState.dispatch {
                requireCodeOperationActive = true
            }
            val result = setupUserRepository.requireConfirmationCode(phoneNumber)
            viewModelState.dispatch {
                requireCodeOperationActive = false
            }
            when (result) {
                is OperationResult.StandardFailure -> {
                    mutableEffect.post(UiEffect.ShowErrorMessage(handleError.map(result.error)))
                }
                is OperationResult.SpecificFailure -> {
                    when (result.error) {
                        RequireConfirmationCodeError.UserBlocked -> {

                        }
                        RequireConfirmationCodeError.WrongCode -> {
                            viewModelState.dispatch {
                                enteredCode = ""
                                codeInputState = CodeInputState.Error
                            }
                        }
                        is RequireConfirmationCodeError.UserTemporaryBlocked -> {

                        }
                    }
                }
                is OperationResult.Success -> {
                    viewModelState.dispatch {
                        enteredCode = ""
                        codeInputState = CodeInputState.Default
                        codeLength = result.data.codeLength
                        codeId = result.data.codeId
                    }
                    startTimer(result.data.resendingTimeInterval.seconds)
                }
            }
        }
    }

    private fun verifyConfirmationCode(codeId: String, code: String) {
        dispatchers.launchBackground(viewModelScope) {
            viewModelState.dispatch {
                requireCodeOperationActive = true
            }
            val result = setupUserRepository.verifyConfirmationCode(
                codeId = codeId,
                code = code
            )
            viewModelState.dispatch {
                requireCodeOperationActive = false
            }
            when (result) {
                is OperationResult.StandardFailure -> {
                    mutableEffect.post(UiEffect.ShowErrorMessage(handleError.map(result.error)))
                }
                is OperationResult.SpecificFailure -> {
                    when (result.error) {
                        is VerifyConfirmationCodeError.UserTemporaryBlocked -> {

                        }
                        VerifyConfirmationCodeError.WrongCode -> {
                            viewModelState.dispatch {
                                codeInputState = CodeInputState.Error
                            }
                        }
                    }
                }
                is OperationResult.Success -> {
                    timerJob?.cancelAndJoin()
                    viewModelState.dispatch {
                        codeInputState = CodeInputState.Success
                    }
                    dispatchers.launchMain(viewModelScope) {
                        delay(500)
                        mutableEffect.post(
                            UiEffect.Navigation(
                                NavigationEvent.ReplaceLast(
                                    Screen.SetupUser.BirthDate())))
                    }
                }
            }
        }
    }

    fun on(event: UserEvent) {
        when (event) {
            UserEvent.ContactSupportButtonClick -> {
                viewModelState.dispatch {
                    inDevelopmentDialogVisible = true
                }
            }
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
                                verifyConfirmationCode(viewModelState.codeId, newCode)
                            }
                        }
                    }
                    KeyboardButtonKey.Delete -> {
                        if (viewModelState.enteredCode.isNotEmpty()) {
                            viewModelState.dispatch {
                                enteredCode = enteredCode.substring(0, enteredCode.lastIndex)
                                codeInputState = CodeInputState.Default
                            }
                        }
                    }
                    KeyboardButtonKey.Fake -> {}
                }
            }
            UserEvent.ResendButtonClick -> {
                if (viewModelState.actual.requireCodeBtnEnabled) {
                    requireConfirmationCode(viewModelState.phoneNumber)
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
            UserEvent.InDevelopmentDialogDismiss -> {
                viewModelState.dispatch {
                    inDevelopmentDialogVisible = false
                }
            }
        }
    }

    sealed class UiEffect {
        data class ShowErrorMessage(val message: String) : UiEffect()
        data class Navigation(val navigationEvent: NavigationEvent) : UiEffect()
    }

    data class UiState(
        val requireCodeProgressbarVisible: Boolean = false,
        val phoneNumber: String = "",
        val resendButtonText: String = "",
        val requireCodeBtnEnabled: Boolean = false,
        val codeCells: List<InputCellData> = listOf(),
        val errorState: ErrorState = ErrorState(false),
        val inDevelopmentDialogVisible: Boolean = false
    )

    private enum class CodeInputState {
        Default,
        Success,
        Error
    }

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data class DigitalKeyPressed(val key: KeyboardButtonKey) : UserEvent()
        data object ResendButtonClick : UserEvent()
        data object ContactSupportButtonClick : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
        data object InDevelopmentDialogDismiss : UserEvent()
    }

    private inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var phoneNumber: PhoneNumber = PhoneNumber()
        var requireCodeOperationActive: Boolean = false
        var remainingTime: Seconds = Seconds(0)
        var codeLength: Int = 0
        var enteredCode: String = ""
        var codeId: String = ""
        var codeInputState: CodeInputState = CodeInputState.Default
        var errorState: ErrorState = ErrorState(false)
        var inDevelopmentDialogVisible: Boolean = false
        val requireCodeBtnEnabled get() = !requireCodeOperationActive && remainingTime.seconds == 0

        override fun implementation() = this

        override fun map(): UiState = UiState(
            requireCodeProgressbarVisible = requireCodeOperationActive,
            phoneNumber = phoneNumber.countryCode + " " + phoneNumber.number,
            resendButtonText = ResendButtonTextStrategy().resolved,
            requireCodeBtnEnabled = requireCodeBtnEnabled,
            codeCells = List(codeLength) { index ->
                InputCellData(
                    text = enteredCode.elementAtOrNull(index)?.toString() ?: "",
                    state = when (codeInputState) {
                        CodeInputState.Default -> if (index == enteredCode.length) InputCellState.Active else InputCellState.Default
                        CodeInputState.Success -> InputCellState.Success
                        CodeInputState.Error -> InputCellState.Error
                    }
                )
            },
            errorState = errorState,
            inDevelopmentDialogVisible = inDevelopmentDialogVisible
        )

        inner class ResendButtonTextStrategy : Strategy<String> {
            override val resolved = when {
                codeInputState == CodeInputState.Success -> ""
                requireCodeBtnEnabled -> manageResources.string(R.string.confirmationCode_resend)
                else -> manageResources.string(
                    R.string.confirmationCode_resendIn,
                    remainingTime.seconds / 60,
                    remainingTime.seconds % 60
                )
            }
        }
    }

    class Base @Inject constructor(
        handleError: HandleError,
        manageResources: ManageResources,
        setupUserRepository: SetupUserRepository,
    ) : ConfirmationCodeViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        manageResources,
        setupUserRepository,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}