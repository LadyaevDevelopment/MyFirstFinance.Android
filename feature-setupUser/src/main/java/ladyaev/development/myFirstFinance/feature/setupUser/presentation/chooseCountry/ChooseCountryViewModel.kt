package ladyaev.development.myFirstFinance.feature.setupUser.presentation.chooseCountry

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.viewModel.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.PhoneNumberScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.models.toUiModel
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelContract
import ladyaev.development.myFirstFinance.feature.setupUser.business.ChooseCountryUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.RequireCountriesUseCase
import javax.inject.Inject

open class ChooseCountryViewModel <StateTransmission : Any, EffectTransmission : Any>(
    private val requireCountriesUseCase: RequireCountriesUseCase,
    private val chooseCountryUseCase: ChooseCountryUseCase,
    private val handleError: HandleError,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel(), ViewModelContract<Unit> {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    override fun initialize(firstTime: Boolean, data: Unit) {
        if (firstTime) {
            requireCountries()
        }
    }

    private fun requireCountries() {
        dispatchers.launchIO(viewModelScope) {
            viewModelState.dispatch {
                loadingData = true
            }
            val result = requireCountriesUseCase.process(viewModelScope)
            viewModelState.dispatch {
                loadingData = false
            }
            when (result) {
                is OperationResult.StandardFailure -> {
                    viewModelState.dispatch {
                        errorState = ErrorState(true, handleError.map(result.error))
                    }
                }
                is OperationResult.SpecificFailure -> {}
                is OperationResult.Success -> {
                    viewModelState.dispatch {
                        countries = result.data.items
                    }
                }
            }
        }
    }

    fun on(event: UserEvent) {
        when (event) {
            is UserEvent.CountryChosen -> {
                viewModelState.dispatch {
                    chosenCountry = event.country
                }
            }
            is UserEvent.QueryChanged -> {
                viewModelState.dispatch {
                    query = event.query
                }
            }
            UserEvent.ToolbarBackButtonClick -> {
                chooseCountryUseCase.process(viewModelState.chosenCountry)
                mutableEffect.post(
                    UiEffect.Navigation(
                        NavigationEvent.PopTo(
                            screen = Screen.SetupUser.PhoneNumber(PhoneNumberScreenArguments(viewModelState.chosenCountry?.toUiModel())),
                            inclusive = false)))
            }
            UserEvent.ErrorDialogDismiss -> {
                viewModelState.dispatch {
                    errorState = ErrorState(false)
                }
            }
        }
    }

    data class UiState(
        val loadingData: Boolean = false,
        val query: String = "",
        val countries: List<CountryItem> = listOf(),
        val errorState: ErrorState = ErrorState(false)
    )

    data class CountryItem(
        val country: ladyaev.development.myfirstfinance.domain.entities.Country,
        val chosen: Boolean
    )

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data class CountryChosen(val country: ladyaev.development.myfirstfinance.domain.entities.Country) : UserEvent()
        data class QueryChanged(val query: String) : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
    }

    inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var loadingData: Boolean = false
        var countries: List<ladyaev.development.myfirstfinance.domain.entities.Country> = listOf()
        var query: String = ""
        var chosenCountry: ladyaev.development.myfirstfinance.domain.entities.Country? = null
        var errorState: ErrorState = ErrorState(false)

        override fun implementation() = this

        override fun map(): UiState = UiState(
            loadingData = loadingData,
            countries = countries
                .filter { it.name.lowercase().contains(query.lowercase().trim()) }
                .map { CountryItem(it, it == chosenCountry) },
            query = query,
            errorState = errorState
        )
    }

    class Base @Inject constructor(
        requireCountriesUseCase: RequireCountriesUseCase,
        chooseCountryUseCase: ChooseCountryUseCase,
        handleError: HandleError
    ) : ChooseCountryViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        requireCountriesUseCase,
        chooseCountryUseCase,
        handleError,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}