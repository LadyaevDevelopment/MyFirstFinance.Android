package ladyaev.development.myFirstFinance.feature.setupUser.presentation.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ResidenceAddressScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.models.toUiModel
import ladyaev.development.myFirstFinance.core.ui.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.misc.FeatureData
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import javax.inject.Inject

open class EmailViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val setupUserRepository: SetupUserRepository,
    private val featureData: FeatureData,
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
            is UserEvent.EmailChanged -> {
                viewModelState.dispatch {
                    email = event.email
                }
            }
            UserEvent.NextButtonClick -> {
                if (viewModelState.actual.nextButtonEnabled) {
                    doOnHideKeyboard {
                        viewModelState.dispatch {
                            bottomSheetVisible = true
                        }
                    }
                }
            }
            UserEvent.ToolbarBackButtonClick -> {
                doOnHideKeyboard {
                    mutableEffect.post(UiEffect.Navigation(NavigationEvent.PopLast))
                }
            }
            UserEvent.EmailBottomSheetDismiss -> {
                dismissBottomSheetAndGoNext()
            }
            UserEvent.EmailBottomSheetNextButtonClick -> {
                dismissBottomSheetAndGoNext()
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

    private fun dismissBottomSheetAndGoNext() {
        if (viewModelState.bottomSheetVisible) {
            viewModelState.dispatch {
                bottomSheetVisible = false
            }
            dispatchers.launchMain(viewModelScope) {
                doOnHideKeyboard {
                    mutableEffect.post(
                        UiEffect.Navigation(
                            NavigationEvent.Navigate(
                                Screen.SetupUser.ResidenceAddress(
                                    ResidenceAddressScreenArguments(
                                        chosenCountry = featureData.country?.toUiModel())))))
                }
            }
        }
    }

    sealed class UiEffect {
        data class ShowErrorMessage(val message: String) : UiEffect()
        data class Navigation(val navigationEvent: NavigationEvent) : UiEffect()
        data object HideKeyboard : UiEffect()
    }

    data class UiState(
        val email: String = "",
        val nextButtonEnabled: Boolean = false,
        val bottomSheetVisible: Boolean = false,
        val errorState: ErrorState = ErrorState(false)
    )

    sealed class UserEvent {
        data object ToolbarBackButtonClick : UserEvent()
        data class EmailChanged(val email: String) : UserEvent()
        data object NextButtonClick : UserEvent()
        data object EmailBottomSheetDismiss : UserEvent()
        data object EmailBottomSheetNextButtonClick : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
    }

    private inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var email: String = ""
        var bottomSheetVisible: Boolean = false
        var errorState: ErrorState = ErrorState(false)

        override fun implementation() = this

        override fun map(): UiState = UiState(
            email = email,
            nextButtonEnabled = email.isNotBlank(),
            bottomSheetVisible = bottomSheetVisible,
            errorState = errorState
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        setupUserRepository: ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository,
        featureData: FeatureData
    ) : EmailViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        setupUserRepository,
        featureData,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}