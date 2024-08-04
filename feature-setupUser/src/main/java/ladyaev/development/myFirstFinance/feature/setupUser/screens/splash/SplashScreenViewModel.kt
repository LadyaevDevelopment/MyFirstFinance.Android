package ladyaev.development.myFirstFinance.feature.setupUser.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import javax.inject.Inject

abstract class SplashScreenViewModel<EffectTransmission : Any>(
    private val dispatchers: ManageDispatchers,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel() {

    val effect: EffectTransmission get() = mutableEffect.read()

    fun initialize(firstTime: Boolean) {
        if (firstTime) {
            dispatchers.launchMain(viewModelScope) {
                delay(3000)
                mutableEffect.post(UiEffect.Navigation(NavigationEvent.ReplaceLast(Screen.SetupUser.StartMenu())))
            }
        }
    }

    sealed class UiEffect {
        data class Navigation(val navigationEvent: NavigationEvent) : UiEffect()
    }

    class Base @Inject constructor(
        dispatchers: ManageDispatchers
    ) : SplashScreenViewModel<LiveData<UiEffect>>(dispatchers, Transmission.SingleLiveEventBase())
}