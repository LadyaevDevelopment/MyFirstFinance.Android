package ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmationCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myFirstFinance.core.common.misc.Milliseconds
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myFirstFinance.core.common.misc.Seconds
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.utils.ManageResources
import ladyaev.development.myFirstFinance.core.common.utils.memoizedComputable
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
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.BaseViewModel
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.viewModel.operations.HandleKeyOperation
import ladyaev.development.myFirstFinance.core.ui.viewModel.operations.RequireConfirmationCodeOperation
import ladyaev.development.myFirstFinance.core.ui.viewModel.operations.VerifyConfirmationCodeOperation
import ladyaev.development.myFirstFinance.feature.setupUser.business.RequireConfirmationCodeUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.VerifyConfirmationCodeUseCase
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError
import javax.inject.Inject

open class ConfirmationCodeViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val manageResources: ManageResources,
    private val requireConfirmationCodeUseCase: RequireConfirmationCodeUseCase,
    private val verifyConfirmationCodeUseCase: VerifyConfirmationCodeUseCase,
    dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    mutableState: Transmission.Mutable<StateTransmission, UiState>,
    mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : BaseViewModel.Stateful<
    StateTransmission,
    EffectTransmission,
    ConfirmationCodeViewModel.UserEvent,
    ConfirmationCodeViewModel.UiState,
    ConfirmationCodeViewModel<StateTransmission, EffectTransmission>.ViewModelState,
    PhoneNumber>(dispatchers, mutableState, mutableEffect) {

    override val viewModelState = ViewModelState()

    private var timerJob: Job? = null

    override fun onInitialized(firstTime: Boolean, data: PhoneNumber) {
        if (firstTime) {
            viewModelState.dispatch {
                phoneNumber = data
            }
            requireConfirmationCode.process(RequireConfirmationCodeOperation.Data(data))
        }
    }

    override fun on(event: UserEvent) {
        when (event) {
            UserEvent.ContactSupportButtonClick -> {
                viewModelState.dispatch {
                    inDevelopmentDialogVisible = true
                }
            }
            is UserEvent.DigitalKeyPressed -> {
                handleKey.process(HandleKeyOperation.Data(
                    event.key,
                    viewModelState.enteredCode,
                    viewModelState.codeLength
                ))
            }
            UserEvent.ResendButtonClick -> {
                requireConfirmationCode.process(RequireConfirmationCodeOperation.Data(viewModelState.phoneNumber))
            }
            UserEvent.ToolbarBackButtonClick -> {
                dispatchEffectSafely(UiEffect.Navigation(NavigationEvent.PopLast))
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

    private val handleKey = object : HandleKeyOperation() {
        override fun onUpdateCode(code: String) = viewModelState.dispatch {
            enteredCode = code
            codeInputState = CodeInputState.Default
        }
        override fun onCodeCompletelyEntered(code: String) {
            verifyConfirmationCode.process(VerifyConfirmationCodeOperation.Data(viewModelState.codeId, Code(code)))
        }
    }

    private val requireConfirmationCode = object : RequireConfirmationCodeOperation(viewModelScope, dispatchers) {
        override fun canProcess() = viewModelState.requireCodeBtnEnabled
        override fun onActivityChanged(isActive: Boolean) = viewModelState.dispatch {
            requireCodeOperationActive = isActive
        }
        override fun onStandardError(error: StandardError) = viewModelState.dispatch {
            errorState = ErrorState(true, handleError.map(error))
        }
        override fun onSuccess(data: RequireConfirmationCodeResult) {
            viewModelState.dispatch {
                enteredCode = ""
                codeInputState = CodeInputState.Default
                codeLength = data.codeLength.data
                codeId = data.codeId
            }
            startTimer(data.resendingTimeInterval.data)
        }
        override suspend fun requireConfirmationCode(phoneNumber: PhoneNumber): OperationResult<RequireConfirmationCodeResult, RequireConfirmationCodeError> {
            return requireConfirmationCodeUseCase.process(phoneNumber)
        }
    }

    private val verifyConfirmationCode = object : VerifyConfirmationCodeOperation(viewModelScope, dispatchers) {
        override fun onActivityChanged(isActive: Boolean) = viewModelState.dispatch {
            requireCodeOperationActive = isActive
        }
        override fun onWrongCode() = viewModelState.dispatch {
            enteredCode = ""
            codeInputState = CodeInputState.Error
        }
        override fun onStandardError(error: StandardError) = viewModelState.dispatch {
            errorState = ErrorState(true, handleError.map(error))
        }
        override fun onSuccess(data: Unit) {
            dispatchers.launchMain(viewModelScope) {
                timerJob?.cancelAndJoin()
                viewModelState.dispatch {
                    codeInputState = CodeInputState.Success
                }
                dispatchEffectSafely(
                    Milliseconds(500),
                    UiEffect.Navigation(
                        NavigationEvent.ReplaceLast(
                            Screen.SetupUser.BirthDate()))
                )
            }
        }
        override suspend fun verifyConfirmationCode(codeId: Id, code: Code): OperationResult<Unit, VerifyConfirmationCodeError> {
            return verifyConfirmationCodeUseCase.process(codeId = codeId, code = code)
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

    data class UiState(
        val requireCodeProgressbarVisible: Boolean = false,
        val phoneNumber: String = "",
        val resendButtonText: String = "",
        val requireCodeBtnEnabled: Boolean = false,
        val codeCells: List<InputCellData> = listOf(),
        val errorState: ErrorState = ErrorState(false),
        val inDevelopmentDialogVisible: Boolean = false
    )

    enum class CodeInputState {
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

    inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var phoneNumber: PhoneNumber = PhoneNumber()
        var requireCodeOperationActive: Boolean = false
        var remainingTime: Seconds = Seconds(0)
        var codeId: Id = Id("")
        var errorState: ErrorState = ErrorState(false)
        var inDevelopmentDialogVisible: Boolean = false
        var codeLength = 0
        var enteredCode = ""
        var codeInputState = CodeInputState.Default

        val requireCodeBtnEnabled get() = !requireCodeOperationActive && remainingTime.data == 0

        private val codeCells = memoizedComputable { codeLength: Int, enteredCode: String, codeInputState: CodeInputState ->
            List(codeLength) { index ->
                InputCellData(
                    text = enteredCode.elementAtOrNull(index)?.toString() ?: "",
                    state = when (codeInputState) {
                        CodeInputState.Default -> if (index == enteredCode.length) InputCellState.Active else InputCellState.Default
                        CodeInputState.Success -> InputCellState.Success
                        CodeInputState.Error -> InputCellState.Error
                    }
                )
            }
        }

        private val resendButtonText get() = when {
            codeInputState == CodeInputState.Success -> ""
            requireCodeBtnEnabled -> manageResources.string(R.string.confirmationCode_resend)
            else -> manageResources.string(
                R.string.confirmationCode_resendIn,
                remainingTime.data / 60,
                remainingTime.data % 60
            )
        }

        override fun implementation() = this

        override fun map(): UiState = UiState(
            requireCodeProgressbarVisible = requireCodeOperationActive,
            phoneNumber = phoneNumber.countryCode + " " + phoneNumber.number,
            resendButtonText = resendButtonText,
            requireCodeBtnEnabled = requireCodeBtnEnabled,
            codeCells = codeCells(codeLength, enteredCode, codeInputState),
            errorState = errorState,
            inDevelopmentDialogVisible = inDevelopmentDialogVisible
        )
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