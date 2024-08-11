package ladyaev.development.myFirstFinance.feature.setupUser.presentation.residenceAddress

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelContract
import ladyaev.development.myFirstFinance.core.ui.viewModel.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyResidenceAddressUseCase
import ladyaev.development.myfirstfinance.domain.entities.Country
import ladyaev.development.myfirstfinance.domain.entities.ResidenceAddress
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import javax.inject.Inject

open class ResidenceAddressViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val specifyResidenceAddressUseCase: SpecifyResidenceAddressUseCase,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel(), ViewModelContract<Country?> {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    override fun initialize(firstTime: Boolean, data: Country?) {
        if (firstTime) {
            viewModelState.dispatch {
                country = data
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
                if (viewModelState.actual.nextButtonEnabled && !viewModelState.operationActive) {
                    specifyResidenceAddress()
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

    private fun specifyResidenceAddress() {
        dispatchers.launchIO(viewModelScope) {
            viewModelState.dispatch {
                operationActive = true
            }
            val result = specifyResidenceAddressUseCase.process(
                ResidenceAddress(
                    country = viewModelState.country!!,
                    city = viewModelState.city,
                    street = viewModelState.street,
                    buildingNumber = viewModelState.buildingNumber,
                    apartment = viewModelState.apartment
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
                            mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.CreatePinCode())))
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
        val countryName: String = "",
        val city: String = "",
        val street: String = "",
        val buildingNumber: String = "",
        val apartment: String = "",
        val nextButtonEnabled: Boolean = false,
        val errorState: ErrorState = ErrorState(false),
        val progressbarVisible: Boolean = false
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
        var operationActive: Boolean = false

        override fun implementation() = this

        override fun map(): UiState = UiState(
            countryName = country?.name ?: "",
            city = city,
            street = street,
            buildingNumber = buildingNumber,
            apartment = apartment,
            nextButtonEnabled = country != null && city.isNotBlank() && street.isNotBlank() && buildingNumber.isNotBlank() && apartment.isNotBlank(),
            errorState = errorState,
            progressbarVisible = operationActive
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        specifyResidenceAddressUseCase: SpecifyResidenceAddressUseCase,
    ) : ResidenceAddressViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        specifyResidenceAddressUseCase,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}