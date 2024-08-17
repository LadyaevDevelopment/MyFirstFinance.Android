package ladyaev.development.myFirstFinance.feature.setupUser.presentation.completeRegistration

import androidx.lifecycle.LiveData
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.BaseViewModel
import javax.inject.Inject

abstract class CompleteRegistrationViewModel<EffectTransmission : Any>(
    dispatchers: ManageDispatchers,
    mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : BaseViewModel<EffectTransmission, CompleteRegistrationViewModel.UserEvent, Unit>(dispatchers, mutableEffect) {

    override fun on(event: UserEvent) {
        when (event) {
            UserEvent.NextButtonClick -> {
                dispatchEffectSafely(
                    UiEffect.Navigation(
                        NavigationEvent.ReplaceLast(
                            Screen.Dashboard.Home())))
            }
        }
    }

    sealed class UserEvent {
        data object NextButtonClick : UserEvent()
    }

    class Base @Inject constructor(
        dispatchers: ManageDispatchers
    ) : CompleteRegistrationViewModel<LiveData<UiEffect>>(dispatchers, Transmission.SingleLiveEventBase())
}