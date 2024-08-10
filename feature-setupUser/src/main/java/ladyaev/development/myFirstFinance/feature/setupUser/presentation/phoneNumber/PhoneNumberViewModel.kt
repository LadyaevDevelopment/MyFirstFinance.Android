package ladyaev.development.myFirstFinance.feature.setupUser.presentation.phoneNumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myFirstFinance.core.common.utils.PhoneNumberValidation
import ladyaev.development.myFirstFinance.core.common.interfaces.Strategy
import ladyaev.development.myFirstFinance.core.di.CountryCache
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.viewModel.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmationCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.models.toUiModel
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelContract
import ladyaev.development.myFirstFinance.feature.setupUser.business.FeatureData
import ladyaev.development.myfirstfinance.domain.entities.Country
import javax.inject.Inject

abstract class PhoneNumberViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val countryCache: CountryCache,
    private val handleError: HandleError,
    private val featureData: FeatureData,
    private val phoneNumberValidation: PhoneNumberValidation,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel(), ViewModelContract<Country?> {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    override fun initialize(firstTime: Boolean, data: Country?) {
        if (firstTime) {
            requireInitialData()
        }
        if (data != null) {
            viewModelState.dispatch {
                country = data
            }
        }
    }

    private fun requireInitialData() {
        dispatchers.launchBackground(viewModelScope) {
            viewModelState.dispatch {
                loadingData = true
            }
            val result = countryCache.data(viewModelScope)
            viewModelState.dispatch {
                loadingData = false
            }
            when (result) {
                is OperationResult.StandardFailure -> {
                    mutableEffect.post(UiEffect.ShowErrorMessage(handleError.map(result.error)))
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

    fun on(event: UserEvent) {
        when (event) {
            UserEvent.CountryFlagClick -> {
                mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.ChooseCountry())))
            }
            is UserEvent.DigitalKeyPressed -> {
                viewModelState.country?.let { country ->
                    val normalizedCountryCode = phoneNumberValidation.normalizedNumber(country.phoneNumberCode)
                    val normalizedPhoneNumber = phoneNumberValidation.normalizedNumber(viewModelState.actual.phoneNumber.toString())

                    when (event.key) {
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
                                    phoneNumber = normalizedPhoneNumber + event.key.string,
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
            UserEvent.ToolbarBackButtonClick -> {
                mutableEffect.post(UiEffect.Navigation(NavigationEvent.PopLast))
            }
            UserEvent.NextButtonClick -> {
                if (viewModelState.actual.nextButtonEnabled) {
                    featureData.country = viewModelState.country
                    mutableEffect.post(
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

    sealed class UiEffect {
        data class ShowErrorMessage(val message: String) : UiEffect()
        data class Navigation(val navigationEvent: NavigationEvent) : UiEffect()
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

        override fun implementation() = this

        override fun map(): UiState = UiState(
            loadingData = loadingData,
            nextButtonEnabled = NextButtonEnabledStrategy().resolved,
            flagPath = country?.flagPath,
            phoneNumber = PhoneNumberStrategy().resolved,
            errorState = errorState
        )

        inner class NextButtonEnabledStrategy : Strategy<Boolean> {
            override val resolved get() = if (phoneNumberValidationResult.formattedPhoneNumber == null) {
                actual.nextButtonEnabled
            } else {
                phoneNumberValidationResult.completed
            }
        }

        inner class PhoneNumberStrategy : Strategy<PhoneNumber> {
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
        featureData: FeatureData,
        phoneNumberValidation: PhoneNumberValidation
    ) : PhoneNumberViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        countryCache,
        handleError,
        featureData,
        phoneNumberValidation,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}