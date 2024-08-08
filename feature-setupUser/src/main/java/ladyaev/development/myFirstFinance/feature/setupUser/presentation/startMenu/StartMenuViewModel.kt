package ladyaev.development.myFirstFinance.feature.setupUser.presentation.startMenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.state.ViewModelStateAbstract
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myfirstfinance.domain.entities.PolicyDocument
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository
import javax.inject.Inject

abstract class StartMenuViewModel<StateTransmission : Any, EffectTransmission : Any>(
    private val miscRepository: MiscRepository,
    private val handleError: HandleError,
    private val dispatchers: ManageDispatchers = ManageDispatchers.Base(),
    private val mutableState: Transmission.Mutable<StateTransmission, UiState>,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel() {

    private val viewModelState = ViewModelState()

    val state: StateTransmission get() = mutableState.read()

    val effect: EffectTransmission get() = mutableEffect.read()

    fun initialize(firstTime: Boolean) {
        if (firstTime) {
            requirePolicyDocuments()
        }
    }

    private fun requirePolicyDocuments() {
        dispatchers.launchBackground(viewModelScope) {
            viewModelState.dispatch {
                loadingDocuments = true
            }
            val result = miscRepository.policyDocuments()
            viewModelState.dispatch {
                loadingDocuments = false
            }
            when (result) {
                is OperationResult.StandardFailure -> {
                    mutableEffect.post(UiEffect.ShowErrorMessage(handleError.map(result.error)))
                }
                is OperationResult.SpecificFailure -> {}
                is OperationResult.Success -> {
                    viewModelState.dispatch {
                        documents = result.data.items
                    }
                }
            }
        }
    }

    fun on(event: UserEvent) {
        when (event) {
            UserEvent.LoginBtnClick -> {
                mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.PhoneNumber(null))))
            }
            UserEvent.RegisterBtnClick -> {
                mutableEffect.post(UiEffect.Navigation(NavigationEvent.Navigate(Screen.SetupUser.PhoneNumber(null))))
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
        val loadingDocuments: Boolean = false,
        val documents: List<PolicyDocument> = listOf(),
        val errorState: ErrorState = ErrorState(false)
    )

    sealed class UserEvent {
        data object LoginBtnClick : UserEvent()
        data object RegisterBtnClick : UserEvent()
        data object ErrorDialogDismiss : UserEvent()
    }

    inner class ViewModelState : ViewModelStateAbstract<UiState, StateTransmission, ViewModelState>(UiState(), viewModelScope, mutableState) {
        var loadingDocuments: Boolean = false
        var documents: List<PolicyDocument> = listOf()
        var errorState: ErrorState = ErrorState(false)

        override fun implementation() = this

        override fun map(): UiState = UiState(
            loadingDocuments = loadingDocuments,
            documents = documents,
            errorState = errorState
        )
    }

    class Base @Inject constructor(
        miscRepository: MiscRepository,
        handleError: HandleError
    ) : StartMenuViewModel<LiveData<UiState>, LiveData<UiEffect>>(
        miscRepository,
        handleError,
        ManageDispatchers.Base(),
        Transmission.LiveDataBase(),
        Transmission.SingleLiveEventBase()
    )
}