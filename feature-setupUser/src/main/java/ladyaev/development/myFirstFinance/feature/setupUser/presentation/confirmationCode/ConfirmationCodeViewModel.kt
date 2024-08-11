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
import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myFirstFinance.core.di.timer
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.input.inputCell.InputCellData
import ladyaev.development.myFirstFinance.core.ui.controls.input.inputCell.InputCellState
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.viewModel.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelContract
import ladyaev.development.myFirstFinance.feature.setupUser.business.RequireConfirmationCodeUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.VerifyConfirmationCodeUseCase
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError
import javax.inject.Inject

open class ConfirmationCodeViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val manageResources: ManageResources,
    private val requireConfirmationCodeUseCase: RequireConfirmationCodeUseCase,
    private val verifyConfirmationCodeUseCase: VerifyConfirmationCodeUseCase,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel(), ViewModelContract<PhoneNumber> {

    private val viewModelState = ViewModelState()

    private var timerJob: Job? = null

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    override fun initialize(firstTime: Boolean, data: PhoneNumber) {
        if (firstTime) {
            viewModelState.dispatch {
                phoneNumber = data
            }
            requireConfirmationCode(data)
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
        dispatchers.launchIO(viewModelScope) {
            viewModelState.dispatch {
                requireCodeOperationActive = true
            }
            val result = requireConfirmationCodeUseCase.process(phoneNumber)
            viewModelState.dispatch {
                requireCodeOperationActive = false
            }
            when (result) {
                is OperationResult.StandardFailure -> {
                    viewModelState.dispatch {
                        errorState = ErrorState(true, handleError.map(result.error))
                    }
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
                        RequireConfirmationCodeError.InvalidData -> {
                            viewModelState.dispatch {
                                errorState = ErrorState(true, handleError.map(StandardError.Unknown(null)))
                            }
                        }
                    }
                }
                is OperationResult.Success -> {
                    viewModelState.dispatch {
                        enteredCode = ""
                        codeInputState = CodeInputState.Default
                        codeLength = result.data.codeLength.data
                        codeId = result.data.codeId
                    }
                    startTimer(result.data.resendingTimeInterval.data)
                }
            }
        }
    }

    private fun verifyConfirmationCode(codeId: Id, code: Code) {
        dispatchers.launchIO(viewModelScope) {
            viewModelState.dispatch {
                requireCodeOperationActive = true
            }
            val result = verifyConfirmationCodeUseCase.process(
                codeId = codeId,
                code = code
            )
            viewModelState.dispatch {
                requireCodeOperationActive = false
            }
            when (result) {
                is OperationResult.StandardFailure -> {
                    viewModelState.dispatch {
                        errorState = ErrorState(true, handleError.map(result.error))
                    }
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
                                verifyConfirmationCode(viewModelState.codeId, Code(newCode))
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
        var codeId: Id = Id("")
        var codeInputState: CodeInputState = CodeInputState.Default
        var errorState: ErrorState = ErrorState(false)
        var inDevelopmentDialogVisible: Boolean = false
        val requireCodeBtnEnabled get() = !requireCodeOperationActive && remainingTime.data == 0

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
                    remainingTime.data / 60,
                    remainingTime.data % 60
                )
            }
        }
    }

    class Base @Inject constructor(
        handleError: HandleError,
        manageResources: ManageResources,
        requireConfirmationCodeUseCase: RequireConfirmationCodeUseCase,
        verifyConfirmationCodeUseCase: VerifyConfirmationCodeUseCase,
    ) : ConfirmationCodeViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        manageResources,
        requireConfirmationCodeUseCase,
        verifyConfirmationCodeUseCase,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}