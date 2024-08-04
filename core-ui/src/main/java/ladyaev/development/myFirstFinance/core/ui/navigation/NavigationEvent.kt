package ladyaev.development.myFirstFinance.core.ui.navigation

sealed class NavigationEvent {
    data class Navigate(val screen: Screen) : NavigationEvent()
    data object PopLast : NavigationEvent()
    data class PopTo(
        val screen: Screen,
        val inclusive: Boolean
    ) : NavigationEvent()
    data class PopAndNavigate(
        val popToScreen: Screen,
        val inclusive: Boolean,
        val screenToShow: Screen
    ) : NavigationEvent()
    data class ReplaceLast(val screen: Screen) : NavigationEvent()
}