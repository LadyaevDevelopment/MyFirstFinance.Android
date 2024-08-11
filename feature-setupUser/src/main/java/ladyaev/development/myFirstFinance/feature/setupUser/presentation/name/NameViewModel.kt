package ladyaev.development.myFirstFinance.feature.setupUser.presentation.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.misc.Name
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.viewModel.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelContract
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyNameUseCase
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import javax.inject.Inject

open class NameViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val specifyNameUseCase: SpecifyNameUseCase,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel(), ViewModelContract<Unit> {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    fun on(event: UserEvent) {
        when (event) {
            is UserEvent.FirstNameChanged -> {
                viewModelState.dispatch {
                    firstName = event.firstName
                }
            }
            is UserEvent.LastNameChanged -> {
                viewModelState.dispatch {
                    lastName = event.lastName
                }
            }
            is UserEvent.MiddleNameChanged -> {
                viewModelState.dispatch {
                    middleName = event.middleName
                }
            }
            UserEvent.NextButtonClick -> {
                if (viewModelState.actual.nextButtonEnabled && !viewModelState.operationActive) {
                    specifyName()
                }
            }
            UserEvent.ToolbarBackButtonClick -> {
                doOnHideKeyboard {
                    mutableEffect.post(UiEffect.Navigation(NavigationEvent.PopLast))
                }
            }
            UserEvent.ErrorDialogDismiss -> {
                viewModelState.dispatch {
                    errorState = ErrorState(false)
                }
            }
        }
    }

    private fun specifyName() {
        dispatchers.launchIO(viewModelScope) {
            viewModelState.dispatch {
                operationActive = true
            }
            val result = specifyNameUseCase.process(
                Name(
                    lastName = viewModelState.lastName,
                    firstName = viewModelState.firstName,
                    middleName = viewModelState.middleName.ifBlank { null }
                )
            )
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
                    dispatchers.launchMain(viewModelScope) {
                        doOnHideKeyboard {
                            mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.Email())))
                        }
                    }
                }
            }
        }
    }

    private fun doOnHideKeyboard(block: () -> Unit) {
        mutableEffect.post(UiEffect.HideKeyboard)
        dispatchers.launchMain(viewModelScope) {
            delay(300)
            block()
        }
    }

    data class UiState(
        val lastName: String = "",
        val firstName: String = "",
        val middleName: String = "",
        val nextButtonEnabled: Boolean = false,
        val errorState: ErrorState = ErrorState(false),
        val progressbarVisible: Boolean = false
    )

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data class FirstNameChanged(val firstName: String) : UserEvent()
        data class LastNameChanged(val lastName: String) : UserEvent()
        data class MiddleNameChanged(val middleName: String) : UserEvent()
        data object NextButtonClick : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
    }

    private inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var lastName: String = ""
        var firstName: String = ""
        var middleName: String = ""
        var errorState: ErrorState = ErrorState(false)
        var operationActive: Boolean = false

        override fun implementation() = this

        override fun map(): UiState = UiState(
            lastName = lastName,
            firstName = firstName,
            middleName = middleName,
            nextButtonEnabled = lastName.isNotBlank() && firstName.isNotBlank(),
            errorState = errorState,
            progressbarVisible = operationActive
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        specifyNameUseCase: SpecifyNameUseCase,
    ) : NameViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        specifyNameUseCase,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}