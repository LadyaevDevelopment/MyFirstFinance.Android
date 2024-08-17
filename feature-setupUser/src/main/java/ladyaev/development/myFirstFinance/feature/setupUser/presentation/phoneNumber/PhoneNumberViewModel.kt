package ladyaev.development.myFirstFinance.feature.setupUser.presentation.phoneNumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ladyaev.development.myFirstFinance.core.common.interfaces.Strategy
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.utils.PhoneNumberValidation
import ladyaev.development.myFirstFinance.core.di.CountryCache
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmationCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.models.toUiModel
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.BaseViewModel
import ladyaev.development.myFirstFinance.core.ui.viewModel.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.feature.setupUser.business.ChooseCountryUseCase
import ladyaev.development.myfirstfinance.domain.entities.Country
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import javax.inject.Inject

abstract class PhoneNumberViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val countryCache: CountryCache,
    private val handleError: HandleError,
    private val phoneNumberValidation: PhoneNumberValidation,
    private val chooseCountryUseCase: ChooseCountryUseCase,
    dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    mutableState: Transmission.Mutable<StateTransmission, UiState>,
    mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : BaseViewModel.Stateful<
    StateTransmission,
    EffectTransmission,
    PhoneNumberViewModel.UserEvent,
    PhoneNumberViewModel.UiState,
    PhoneNumberViewModel<StateTransmission, EffectTransmission>.ViewModelState,
    PhoneNumberViewModel.InputData>(dispatchers, mutableState, mutableEffect) {

    override val viewModelState = ViewModelState()

    override fun onInitialized(firstTime: Boolean, data: InputData) {
        if (firstTime) {
            requireCountries()
        }
        viewModelState.dispatch {
            country = data.country
            phoneNumberValidationResult = PhoneNumberValidation.TestResult(PhoneNumber(), false)
        }
    }

    override fun on(event: UserEvent) {
        when (event) {
            UserEvent.CountryFlagClick -> {
                dispatchEffectSafely(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.ChooseCountry())))
            }
            is UserEvent.DigitalKeyPressed -> {
                handleKey(event.key)
            }
            UserEvent.ToolbarBackButtonClick -> {
                dispatchEffectSafely(UiEffect.Navigation(NavigationEvent.PopLast))
            }
            UserEvent.NextButtonClick -> {
                if (viewModelState.actual.nextButtonEnabled) {
                    chooseCountryUseCase.process(viewModelState.country)
                    dispatchEffectSafely(
                        UiEffect.Navigation(
                            NavigationEvent.Navigate(
                                screen = Screen.SetupUser.ConfirmationCode(
                                    ConfirmationCodeScreenArguments(viewModelState.actual.phoneNumber.toUiModel())))))
                }
            }
            UserEvent.ClearPhoneNumberBtnClick -> {
                viewModelState.dispatch {
                    phoneNumberValidationResult = PhoneNumberValidation.TestResult(PhoneNumber(), false)
                }
            }
            UserEvent.ErrorDialogDismiss -> {
                viewModelState.dispatch {
                    errorState = ErrorState(false)
                }
            }
        }
    }

    private fun requireCountries() {
        dispatchers.launchMain(viewModelScope) {
            viewModelState.dispatch {
                loadingData = true
            }
            val result = countryCache.data()
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
                        country = result.data.items.firstOrNull()
                    }
                }
            }
        }
    }

    private fun handleKey(key: KeyboardButtonKey) {
        viewModelState.country?.let { country ->
            val normalizedCountryCode = phoneNumberValidation.normalizedNumber(country.phoneNumberCode)
            val normalizedPhoneNumber = phoneNumberValidation.normalizedNumber(viewModelState.actual.phoneNumber.toString())

            when (key) {
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
                    viewModelState.dispatch {
                        phoneNumberValidationResult = phoneNumberValidation.test(
                            phoneNumber = normalizedPhoneNumber + key.string,
                            phoneNumberMasks = viewModelState.country?.let { country ->
                                country.phoneNumberMasks.map { mask -> PhoneNumber(country.phoneNumberCode, mask) }
                            } ?: listOf()
                        )
                    }
                }
                KeyboardButtonKey.Delete -> {
                    if (normalizedPhoneNumber.length > normalizedCountryCode.length) {
                        viewModelState.dispatch {
                            phoneNumberValidationResult = phoneNumberValidation.test(
                                phoneNumber = normalizedPhoneNumber.substring(0, normalizedPhoneNumber.lastIndex),
                                phoneNumberMasks = viewModelState.country?.let { country ->
                                    country.phoneNumberMasks.map { mask -> PhoneNumber(country.phoneNumberCode, mask) }
                                } ?: listOf()
                            )
                        }
                    }
                }
                KeyboardButtonKey.Fake -> {}
            }
        }
    }

    data class UiState(
        val loadingData: Boolean = false,
        val nextButtonEnabled: Boolean = false,
        val flagPath: String? = null,
        val phoneNumber: PhoneNumber = PhoneNumber(),
        val errorState: ErrorState = ErrorState(false)
    )

    sealed class UserEvent {
        data object CountryFlagClick : UserEvent()
        data object ToolbarBackButtonClick : UserEvent()
        data object NextButtonClick : UserEvent()
        data object ClearPhoneNumberBtnClick : UserEvent()
        data class DigitalKeyPressed(val key: KeyboardButtonKey) : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
    }

    inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var country: Country? = null
        var loadingData: Boolean = false
        var phoneNumberValidationResult = PhoneNumberValidation.TestResult(PhoneNumber(), false)
        var errorState: ErrorState = ErrorState(false)

        private val nextButtonEnabledStrategy = NextButtonEnabledStrategy()
        private val phoneNumberStrategy = PhoneNumberStrategy()

        override fun implementation() = this

        override fun map(): UiState = UiState(
            loadingData = loadingData,
            nextButtonEnabled = nextButtonEnabledStrategy.resolved,
            flagPath = country?.flagPath,
            phoneNumber = phoneNumberStrategy.resolved,
            errorState = errorState
        )

        private inner class NextButtonEnabledStrategy : Strategy<Boolean> {
            override val resolved get() = if (phoneNumberValidationResult.formattedPhoneNumber == null) {
                actual.nextButtonEnabled
            } else {
                phoneNumberValidationResult.completed
            }
        }

        private inner class PhoneNumberStrategy : Strategy<PhoneNumber> {
            private val actualPhoneNumber get() = phoneNumberValidationResult.formattedPhoneNumber ?: actual.phoneNumber

            override val resolved get() = if (actualPhoneNumber.isEmpty) {
                country?.let { PhoneNumber(it.phoneNumberCode) } ?: PhoneNumber()
            } else {
                actualPhoneNumber
            }
        }
    }

    class Base @Inject constructor(
        countryCache: CountryCache,
        handleError: HandleError,
        phoneNumberValidation: PhoneNumberValidation,
        chooseCountryUseCase: ChooseCountryUseCase,
    ) : PhoneNumberViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        countryCache,
        handleError,
        phoneNumberValidation,
        chooseCountryUseCase,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )

    data class InputData(val country: Country?)
}