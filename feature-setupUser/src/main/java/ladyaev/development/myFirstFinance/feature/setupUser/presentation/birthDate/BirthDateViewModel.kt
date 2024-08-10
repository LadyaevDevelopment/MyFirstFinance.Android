package ladyaev.development.myFirstFinance.feature.setupUser.presentation.birthDate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ladyaev.development.myFirstFinance.core.common.utils.CurrentDate
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.utils.ManageResources
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.extensions.to2day2month4yearFormat
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelContract
import ladyaev.development.myFirstFinance.core.ui.viewModel.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyBirthDateUseCase
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.specifyBirthDate.SpecifyBirthDateError
import java.util.Date
import javax.inject.Inject

open class BirthDateViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val manageResources: ManageResources,
    private val currentDate: CurrentDate,
    private val specifyBirthDateUseCase: SpecifyBirthDateUseCase,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel(), ViewModelContract<Unit> {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    fun on(event: UserEvent) {
        when (event) {
            UserEvent.ToolbarBackButtonClick -> {
                mutableEffect.post(UiEffect.Navigation(NavigationEvent.PopLast))
            }
            is UserEvent.DateChanged -> {
                viewModelState.dispatch {
                    date = event.date
                }
            }
            UserEvent.DateInputClick -> {
                mutableEffect.post(UiEffect.ShowDatePickerDialog)
            }
            UserEvent.NextButtonClick -> {
                if (viewModelState.actual.nextButtonEnabled) {
                    specifyBirthDate(viewModelState.date ?: Date())
                }
            }
            UserEvent.ErrorDialogDismiss -> {
                viewModelState.dispatch {
                    errorState = ErrorState(false)
                }
            }
        }
    }

    private fun specifyBirthDate(birthDate: Date) {
        dispatchers.launchBackground(viewModelScope) {
            viewModelState.dispatch {
                operationActive = true
            }
            val result = specifyBirthDateUseCase.process(birthDate)
            viewModelState.dispatch {
                operationActive = false
            }

            when (result) {
                is OperationResult.SpecificFailure -> {
                    when (result.error) {
                        SpecifyBirthDateError.UserIsMinor -> {
                            viewModelState.dispatch {
                                errorState = ErrorState(
                                    true,
                                    manageResources.string(R.string.birthDate_error_minorAge)
                                )
                            }
                        }
                        SpecifyBirthDateError.InvalidData -> {
                            viewModelState.dispatch {
                                errorState = ErrorState(
                                    true,
                                    handleError.map(StandardError.Unknown(null))
                                )
                            }
                        }
                    }
                }
                is OperationResult.StandardFailure -> {
                    viewModelState.dispatch {
                        errorState = ErrorState(
                            true,
                            handleError.map(result.error)
                        )
                    }
                }
                is OperationResult.Success -> {
                    mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.Name())))
                }
            }
        }
    }

    sealed class UiEffect {
        data class Navigation(val navigationEvent: NavigationEvent) : UiEffect()
        data object ShowDatePickerDialog : UiEffect()
    }

    data class UiState(
        val displayedDate: String = "",
        val date: Date? = null,
        val nextButtonEnabled: Boolean = false,
        val errorState: ErrorState = ErrorState(false),
        val progressbarVisible: Boolean = false
    )

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data object DateInputClick : UserEvent()
        data class DateChanged(val date: Date) : UserEvent()
        data object NextButtonClick : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
    }

    private inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var date: Date? = null
        var errorState: ErrorState = ErrorState(false)
        var operationActive: Boolean = false

        override fun implementation() = this

        override fun map(): UiState = UiState(
            displayedDate = date?.to2day2month4yearFormat() ?: "",
            date = date,
            nextButtonEnabled = date != null,
            errorState = errorState,
            progressbarVisible = operationActive
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        manageResources: ManageResources,
        currentDate: CurrentDate,
        specifyBirthDateUseCase: SpecifyBirthDateUseCase,
    ) : BirthDateViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        manageResources,
        currentDate,
        specifyBirthDateUseCase,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}