package ladyaev.development.myFirstFinance.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmPinCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmationCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.PhoneNumberScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ResidenceAddressScreenArguments
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.birthDate.BirthDateScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.chooseCountry.ChooseCountryScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.completeRegistration.CompleteRegistrationScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmPinCodeScreen.ConfirmPinCodeScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmationCode.ConfirmationCodeScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.createPinCode.CreatePinCodeScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.email.EmailScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.name.NameScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.phoneNumber.PhoneNumberScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.residenceAddress.ResidenceAddressScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.splash.SplashScreen
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.startMenu.StartMenuScreen
import ladyaev.development.myFirstFinance.featureDashboard.home.HomeScreen

private const val ARGUMENT_KEY = "argument"

@Composable
fun NavigationContainer(application: MainApplication) {
    val navController = rememberNavController()

    val handleNavigationEvent = remember {
        { navigationEvent: NavigationEvent ->
            when (navigationEvent) {
                NavigationEvent.PopLast -> navController.popBackStack()
                is NavigationEvent.PopTo -> {
                    navController.popBackStack(
                        navigationEvent.screen.routeName,
                        inclusive = false
                    )
                    if (navigationEvent.screen.arguments != null) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(ARGUMENT_KEY, navigationEvent.screen.arguments)
                    }
                }
                is NavigationEvent.Navigate -> {
                    navController.navigate(navigationEvent.screen.routeName)
                    if (navigationEvent.screen.arguments != null) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(ARGUMENT_KEY, navigationEvent.screen.arguments)
                    }
                }
                is NavigationEvent.PopAndNavigate -> {
                    navController.popBackStack(
                        navigationEvent.popToScreen.routeName,
                        inclusive = navigationEvent.inclusive
                    )
                    navController.navigate(navigationEvent.screenToShow.routeName)
                    if (navigationEvent.screenToShow.arguments != null) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(ARGUMENT_KEY, navigationEvent.screenToShow.arguments)
                    }
                }
                is NavigationEvent.ReplaceLast -> {
                    navController.popBackStack()
                    navController.navigate(navigationEvent.screen.routeName)
                    if (navigationEvent.screen.arguments != null) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(ARGUMENT_KEY, navigationEvent.screen.arguments)
                    }
                }
            }
            Unit
        }
    }

    val setupUserViewModelFactory = {
        application.setupUserComponent.viewModelFactory
    }

    NavHost(navController, startDestination = Screen.SetupUser.Splash.ROUTE_NAME) {
        composable(Screen.SetupUser.Splash.ROUTE_NAME) {
            SplashScreen(setupUserViewModelFactory, handleNavigationEvent)
        }
        composable(Screen.SetupUser.StartMenu.ROUTE_NAME) {
            StartMenuScreen(setupUserViewModelFactory, handleNavigationEvent)
        }
        composable(Screen.SetupUser.PhoneNumber.ROUTE_NAME) { backStackEntry ->
            val arguments = backStackEntry.savedStateHandle.get<PhoneNumberScreenArguments>(ARGUMENT_KEY) ?: PhoneNumberScreenArguments()
            PhoneNumberScreen(setupUserViewModelFactory, arguments, handleNavigationEvent)
        }
        composable(Screen.SetupUser.ChooseCountry.ROUTE_NAME) {
            ChooseCountryScreen(setupUserViewModelFactory, handleNavigationEvent)
        }
        composable(Screen.SetupUser.ConfirmationCode.ROUTE_NAME) { backStackEntry ->
            val arguments = backStackEntry.savedStateHandle.get<ConfirmationCodeScreenArguments>(ARGUMENT_KEY) ?: ConfirmationCodeScreenArguments()
            ConfirmationCodeScreen(setupUserViewModelFactory, arguments, handleNavigationEvent)
        }
        composable(Screen.SetupUser.CreatePinCode.ROUTE_NAME) {
            CreatePinCodeScreen(setupUserViewModelFactory, handleNavigationEvent)
        }
        composable(Screen.SetupUser.ConfirmPinCode.ROUTE_NAME) { backStackEntry ->
            val arguments = backStackEntry.savedStateHandle.get<ConfirmPinCodeScreenArguments>(ARGUMENT_KEY) ?: ConfirmPinCodeScreenArguments()
            ConfirmPinCodeScreen(setupUserViewModelFactory, arguments, handleNavigationEvent)
        }
        composable(Screen.SetupUser.BirthDate.ROUTE_NAME) {
            BirthDateScreen(setupUserViewModelFactory, handleNavigationEvent)
        }
        composable(Screen.SetupUser.Name.ROUTE_NAME) {
            NameScreen(setupUserViewModelFactory, handleNavigationEvent)
        }
        composable(Screen.SetupUser.Email.ROUTE_NAME) {
            EmailScreen(setupUserViewModelFactory, handleNavigationEvent)
        }
        composable(Screen.SetupUser.ResidenceAddress.ROUTE_NAME) { backStackEntry ->
            val arguments = backStackEntry.savedStateHandle.get<ResidenceAddressScreenArguments>(ARGUMENT_KEY) ?: ResidenceAddressScreenArguments()
            ResidenceAddressScreen(setupUserViewModelFactory, arguments, handleNavigationEvent)
        }
        composable(Screen.SetupUser.CompleteRegistration.ROUTE_NAME) {
            CompleteRegistrationScreen(setupUserViewModelFactory, handleNavigationEvent)
        }
        composable(Screen.Dashboard.Home.ROUTE_NAME) {
            HomeScreen()
        }
    }
}