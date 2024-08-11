package ladyaev.development.myFirstFinance.feature.setupUser.presentation.completeRegistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelContract
import javax.inject.Inject

abstract class CompleteRegistrationViewModel<EffectTransmission : Any>(
    private val dispatchers: ManageDispatchers,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel(), ViewModelContract<Unit> {

    val effect: EffectTransmission get() = mutableEffect.read()

    fun on(event: UserEvent) {
        when (event) {
            UserEvent.NextButtonClick -> {
                mutableEffect.post(
                    UiEffect.Navigation(
                        NavigationEvent.ReplaceLast(
                            Screen.Dashboard.Home()))
                )
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