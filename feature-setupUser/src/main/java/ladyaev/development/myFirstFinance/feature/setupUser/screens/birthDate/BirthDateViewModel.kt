package ladyaev.development.myFirstFinance.feature.setupUser.screens.birthDate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ladyaev.development.myFirstFinance.core.common.Age
import ladyaev.development.myFirstFinance.core.common.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.ManageResources
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.extensions.to2day2month4yearFormat
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.domain.repository.setupUser.SetupUserRepository
import ladyaev.development.myFirstFinance.core.resources.R
import java.util.Date
import javax.inject.Inject

open class BirthDateViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val manageResources: ManageResources,
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

        }
    }

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
                    if (Age(viewModelState.date ?: Date()).years >= 18) {
                        mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.Name())))
                    } else {
                        viewModelState.dispatch {
                            errorState = ErrorState(
                                true,
                                manageResources.string(R.string.birthDate_error_minorAge)
                            )
                        }
                    }
                }
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
        data object ShowDatePickerDialog : UiEffect()
    }

    data class UiState(
        val displayedDate: String = "",
        val date: Date? = null,
        val nextButtonEnabled: Boolean = false,
        val errorState: ErrorState = ErrorState(false)
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

        override fun implementation() = this

        override fun map(): UiState = UiState(
            displayedDate = date?.to2day2month4yearFormat() ?: "",
            date = date,
            nextButtonEnabled = date != null,
            errorState = errorState
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        manageResources: ManageResources,
        setupUserRepository: SetupUserRepository,
    ) : BirthDateViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        manageResources,
        setupUserRepository,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}