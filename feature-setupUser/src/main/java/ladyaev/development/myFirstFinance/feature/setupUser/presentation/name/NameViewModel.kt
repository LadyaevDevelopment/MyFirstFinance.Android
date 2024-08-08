package ladyaev.development.myFirstFinance.feature.setupUser.presentation.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import javax.inject.Inject

open class NameViewModel<StateTransmission : Any, EffectTransmission : Any>(
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

        }
    }

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
                if (viewModelState.actual.nextButtonEnabled) {
                    doOnHideKeyboard {
                        mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.Email())))
                    }
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

    private fun doOnHideKeyboard(block: () -> Unit) {
        mutableEffect.post(UiEffect.HideKeyboard)
        dispatchers.launchMain(viewModelScope) {
            delay(300)
            block()
        }
    }

    sealed class UiEffect {
        data class ShowErrorMessage(val message: String) : UiEffect()
        data class Navigation(val navigationEvent: NavigationEvent) : UiEffect()
        data object HideKeyboard : UiEffect()
    }

    data class UiState(
        val lastName: String = "",
        val firstName: String = "",
        val middleName: String = "",
        val nextButtonEnabled: Boolean = false,
        val errorState: ErrorState = ErrorState(false)
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

        override fun implementation() = this

        override fun map(): UiState = UiState(
            lastName = lastName,
            firstName = firstName,
            middleName = middleName,
            nextButtonEnabled = lastName.isNotBlank() && firstName.isNotBlank(),
            errorState = errorState
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        setupUserRepository: ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository,
    ) : NameViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        setupUserRepository,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}