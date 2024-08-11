package ladyaev.development.myFirstFinance.core.ui.effects

import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent

sealed class UiEffect {
    data class Navigation(val navigationEvent: NavigationEvent) : UiEffect()
    data object HideKeyboard : UiEffect()
}