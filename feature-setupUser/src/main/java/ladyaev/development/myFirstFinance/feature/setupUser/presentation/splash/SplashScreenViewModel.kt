package ladyaev.development.myFirstFinance.feature.setupUser.presentation.splash

import androidx.lifecycle.LiveData
import ladyaev.development.myFirstFinance.core.common.misc.Milliseconds
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.BaseViewModel
import javax.inject.Inject

abstract class SplashScreenViewModel<EffectTransmission : Any>(
    dispatchers: ManageDispatchers,
    mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : BaseViewModel<EffectTransmission, Unit, Unit>(dispatchers, mutableEffect) {

    override fun initialize(firstTime: Boolean, data: Unit) {
        if (firstTime) {
            dispatchEffectSafely(
                Milliseconds(3000),
                UiEffect.Navigation(NavigationEvent.ReplaceLast(Screen.SetupUser.StartMenu())))
        }
    }

    class Base @Inject constructor(
        dispatchers: ManageDispatchers
    ) : SplashScreenViewModel<LiveData<UiEffect>>(dispatchers, Transmission.SingleLiveEventBase())
}