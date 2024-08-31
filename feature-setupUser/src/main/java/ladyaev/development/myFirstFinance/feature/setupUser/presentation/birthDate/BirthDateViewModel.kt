package ladyaev.development.myFirstFinance.feature.setupUser.presentation.birthDate

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ladyaev.development.myFirstFinance.core.common.utils.CurrentDate
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.utils.ManageResources
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.extensions.to2day2month4yearFormat
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.BaseViewModel
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.feature.setupUser.business.RequireChosenCountryUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyBirthDateUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.navigation.UserStatusToScreen
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
    private val requireChosenCountryUseCase: RequireChosenCountryUseCase,
    dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    mutableState: Transmission.Mutable<StateTransmission, UiState>,
    mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : BaseViewModel.Stateful<
    StateTransmission,
    EffectTransmission,
    BirthDateViewModel.UserEvent,
    BirthDateViewModel.UiState,
    BirthDateViewModel<StateTransmission, EffectTransmission>.ViewModelState,
    Unit>(dispatchers, mutableState, mutableEffect) {

    override val viewModelState = ViewModelState()

    override fun on(event: UserEvent) {
        when (event) {
            UserEvent.ToolbarBackButtonClick -> {
                dispatchEffectSafely(UiEffect.Navigation(NavigationEvent.PopLast))
            }
            is UserEvent.DateChanged -> {
                viewModelState.dispatch {
                    date = event.date
                }
            }
            UserEvent.DateInputClick -> {
                viewModelState.dispatch {
                    datePickerDialogVisible = true
                }
            }
            UserEvent.NextButtonClick -> {
                if (viewModelState.actual.nextButtonEnabled && !viewModelState.operationActive) {
                    specifyBirthDate()
                }
            }
            UserEvent.ErrorDialogDismiss -> {
                viewModelState.dispatch {
                    errorState = ErrorState(false)
                }
            }
            UserEvent.DatePickerDialogDismiss -> {
                viewModelState.dispatch {
                    datePickerDialogVisible = false
                }
            }
        }
    }

    private fun specifyBirthDate() {
        dispatchers.launchIO(viewModelScope) {
            viewModelState.dispatch {
                operationActive = true
            }
            val result = specifyBirthDateUseCase.process(viewModelState.date ?: Date())
            viewModelState.dispatch {
                operationActive = false
            }

            when (result) {
                is OperationResult.SpecificFailure -> {
                    when (result.error) {
                        SpecifyBirthDateError.UserIsMinor -> {
                            viewModelState.dispatch {
                                errorState = ErrorState(true, manageResources.string(R.string.birthDate_error_minorAge))
                            }
                        }
                        SpecifyBirthDateError.InvalidData -> {
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
                        UiEffect.Navigation(
                            NavigationEvent.Navigate(
                                UserStatusToScreen(chosenCountry).map(result.data.userStatus))))
                }
            }
        }
    }

    data class UiState(
        val displayedDate: String = "",
        val date: Date? = null,
        val nextButtonEnabled: Boolean = false,
        val errorState: ErrorState = ErrorState(false),
        val progressbarVisible: Boolean = false,
        val datePickerDialogVisible: Boolean = false,
    )

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data object DateInputClick : UserEvent()
        data class DateChanged(val date: Date) : UserEvent()
        data object NextButtonClick : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
        data object DatePickerDialogDismiss : UserEvent()
    }

    inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var date: Date? = null
        var errorState: ErrorState = ErrorState(false)
        var operationActive: Boolean = false
        var datePickerDialogVisible: Boolean = false

        override fun implementation() = this

        override fun map(): UiState = UiState(
            displayedDate = date?.to2day2month4yearFormat() ?: "",
            date = date,
            nextButtonEnabled = date != null,
            errorState = errorState,
            progressbarVisible = operationActive,
            datePickerDialogVisible = datePickerDialogVisible
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        manageResources: ManageResources,
        currentDate: CurrentDate,
        specifyBirthDateUseCase: SpecifyBirthDateUseCase,
        requireChosenCountryUseCase: RequireChosenCountryUseCase
    ) : BirthDateViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        manageResources,
        currentDate,
        specifyBirthDateUseCase,
        requireChosenCountryUseCase,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}