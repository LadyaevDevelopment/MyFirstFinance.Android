package ladyaev.development.myFirstFinance.feature.setupUser.presentation.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.misc.Email
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ResidenceAddressScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.models.toUiModel
import ladyaev.development.myFirstFinance.core.ui.viewModel.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelContract
import ladyaev.development.myFirstFinance.feature.setupUser.business.FeatureData
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyEmailUseCase
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import javax.inject.Inject

open class EmailViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val handleError: HandleError,
    private val specifyEmailUseCase: SpecifyEmailUseCase,
    private val featureData: FeatureData,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel(), ViewModelContract<Unit> {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    fun on(event: UserEvent) {
        when (event) {
            is UserEvent.EmailChanged -> {
                viewModelState.dispatch {
                    email = event.email
                }
            }
            UserEvent.NextButtonClick -> {
                if (viewModelState.actual.nextButtonEnabled && !viewModelState.operationActive) {
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
            specifyEmail()
        }
    }

    private fun specifyEmail() {
        dispatchers.launchIO(viewModelScope) {
            viewModelState.dispatch {
                operationActive = true
            }
            val result = specifyEmailUseCase.process(Email(viewModelState.email))
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
                            mutableEffect.post(
                                UiEffect.Navigation(
                                    NavigationEvent.Navigate(
                                        Screen.SetupUser.ResidenceAddress(
                                            ResidenceAddressScreenArguments(chosenCountry = featureData.country?.toUiModel())))))
                        }
                    }
                }
            }
        }
    }

    data class UiState(
        val email: String = "",
        val nextButtonEnabled: Boolean = false,
        val bottomSheetVisible: Boolean = false,
        val errorState: ErrorState = ErrorState(false),
        val progressbarVisible: Boolean = false
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
        var operationActive: Boolean = false

        override fun implementation() = this

        override fun map(): UiState = UiState(
            email = email,
            nextButtonEnabled = email.isNotBlank(),
            bottomSheetVisible = bottomSheetVisible,
            errorState = errorState,
            progressbarVisible = operationActive
        )
    }

    class Base @Inject constructor(
        handleError: HandleError,
        specifyEmailUseCase: SpecifyEmailUseCase,
        featureData: FeatureData
    ) : EmailViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        handleError,
        specifyEmailUseCase,
        featureData,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}