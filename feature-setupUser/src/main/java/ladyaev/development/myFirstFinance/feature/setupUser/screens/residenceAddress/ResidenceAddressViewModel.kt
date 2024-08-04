package ladyaev.development.myFirstFinance.feature.setupUser.screens.residenceAddress

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.domain.entities.Country
import ladyaev.development.myFirstFinance.domain.repository.setupUser.SetupUserRepository
import javax.inject.Inject

open class ResidenceAddressViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val setupUserRepository: SetupUserRepository,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel() {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    fun initialize(firstTime: Boolean, chosenCountry: Country?) {
        if (firstTime) {
            viewModelState.dispatch {
                country = chosenCountry
            }
        }
    }

    fun on(event: UserEvent) {
        when (event) {
            is UserEvent.CityChanged -> {
                viewModelState.dispatch {
                    city = event.city
                }
            }
            is UserEvent.StreetChanged -> {
                viewModelState.dispatch {
                    street = event.street
                }
            }
            is UserEvent.BuildingNumberChanged -> {
                viewModelState.dispatch {
                    buildingNumber = event.buildingNumber
                }
            }
            is UserEvent.ApartmentChanged -> {
                viewModelState.dispatch {
                    apartment = event.apartment
                }
            }
            UserEvent.NextButtonClick -> {
                if (viewModelState.actual.nextButtonEnabled) {
                    doOnHideKeyboard {
                        mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.CreatePinCode())))
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
        val countryName: String = "",
        val city: String = "",
        val street: String = "",
        val buildingNumber: String = "",
        val apartment: String = "",
        val nextButtonEnabled: Boolean = false,
        val errorState: ErrorState = ErrorState(false)
    )

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data class CityChanged(val city: String) : UserEvent()
        data class StreetChanged(val street: String) : UserEvent()
        data class BuildingNumberChanged(val buildingNumber: String) : UserEvent()
        data class ApartmentChanged(val apartment: String) : UserEvent()
        data object NextButtonClick : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
    }

    private inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var country: Country? = null
        var city: String = ""
        var street: String = ""
        var buildingNumber: String = ""
        var apartment: String = ""
        var errorState: ErrorState = ErrorState(false)

        override fun implementation() = this

        override fun map(): UiState = UiState(
            countryName = country?.name ?: "",
            city = city,
            street = street,
            buildingNumber = buildingNumber,
            apartment = apartment,
            nextButtonEnabled = country != null && city.isNotBlank() && street.isNotBlank() && buildingNumber.isNotBlank() && apartment.isNotBlank(),
            errorState = errorState
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        setupUserRepository: SetupUserRepository,
    ) : ResidenceAddressViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        setupUserRepository,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}