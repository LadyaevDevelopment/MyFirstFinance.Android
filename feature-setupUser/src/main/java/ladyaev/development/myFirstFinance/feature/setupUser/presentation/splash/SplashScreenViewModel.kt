package ladyaev.development.myFirstFinance.feature.setupUser.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.transmission.Transmission
import ladyaev.development.myFirstFinance.core.ui.viewModel.ViewModelContract
import javax.inject.Inject

abstract class SplashScreenViewModel<EffectTransmission : Any>(
    private val dispatchers: ManageDispatchers,
    private val mutableEffect: Transmission.Mutable<EffectTransmission, UiEffect>
) : ViewModel(), ViewModelContract<Unit> {

    val effect: EffectTransmission get() = mutableEffect.read()

    override fun initialize(firstTime: Boolean, data: Unit) {
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