package ladyaev.development.myFirstFinance.feature.setupUser.screens.completeRegistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ladyaev.development.myFirstFinance.core.common.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import javax.inject.Inject

abstract class CompleteRegistrationViewModel<EffectTransmission : Any>(
    private val dispatchers: ManageDispatchers,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel() {

    val effect: EffectTransmission get() = mutableEffect.read()

    fun initialize(firstTime: Boolean) {

    }

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

    sealed class UiEffect {
        data class Navigation(val navigationEvent: NavigationEvent) : UiEffect()
    }

    sealed class UserEvent {
        data object NextButtonClick : UserEvent()
    }

    class Base @Inject constructor(
        dispatchers: ManageDispatchers
    ) : CompleteRegistrationViewModel<LiveData<UiEffect>>(dispatchers, Transmission.SingleLiveEventBase())
}