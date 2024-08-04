package ladyaev.development.myFirstFinance.core.ui.navigation

import android.os.Parcelable
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmPinCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmationCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.PhoneNumberScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ResidenceAddressScreenArguments

sealed class Screen(val routeName: String, val arguments: Parcelable?) {
    sealed class SetupUser(routeName: String, arguments: Parcelable? = null) : Screen(routeName, arguments) {
        class Splash : SetupUser(ROUTE_NAME) {
            companion object {
                const val ROUTE_NAME: String = "splash_screen"
            }
        }
        class StartMenu : SetupUser(ROUTE_NAME) {
            companion object {
                const val ROUTE_NAME: String = "start_menu_screen"
            }
        }
        class PhoneNumber(arguments: PhoneNumberScreenArguments?) : SetupUser(ROUTE_NAME, arguments) {
            companion object {
                const val ROUTE_NAME: String = "phone_number_screen"
            }
        }
        class ChooseCountry : SetupUser(ROUTE_NAME) {
            companion object {
                const val ROUTE_NAME: String = "choose_country_screen"
            }
        }
        class ConfirmationCode(arguments: ConfirmationCodeScreenArguments?) : SetupUser(ROUTE_NAME, arguments) {
            companion object {
                const val ROUTE_NAME: String = "confirmation_code_screen"
            }
        }
        class CreatePinCode : SetupUser(ROUTE_NAME) {
            companion object {
                const val ROUTE_NAME: String = "create_pin_code_screen"
            }
        }
        class ConfirmPinCode(arguments: ConfirmPinCodeScreenArguments?) : SetupUser(ROUTE_NAME, arguments) {
            companion object {
                const val ROUTE_NAME: String = "confirm_pin_code_screen"
            }
        }
        class BirthDate : SetupUser(ROUTE_NAME) {
            companion object {
                const val ROUTE_NAME: String = "birth_date_screen"
            }
        }
        class Name : SetupUser(ROUTE_NAME) {
            companion object {
                const val ROUTE_NAME: String = "name_screen"
            }
        }
        class Email : SetupUser(ROUTE_NAME) {
            companion object {
                const val ROUTE_NAME: String = "email_screen"
            }
        }
        class ResidenceAddress(arguments: ResidenceAddressScreenArguments?) : SetupUser(ROUTE_NAME, arguments) {
            companion object {
                const val ROUTE_NAME: String = "residence_address_screen"
            }
        }
        class CompleteRegistration : SetupUser(ROUTE_NAME) {
            companion object {
                const val ROUTE_NAME: String = "complete_registration_screen"
            }
        }
    }

    sealed class Dashboard(routeName: String, arguments: Parcelable? = null) : Screen(routeName, arguments) {
        class Home : SetupUser(ROUTE_NAME) {
            companion object {
                const val ROUTE_NAME: String = "home_screen"
            }
        }
    }
}