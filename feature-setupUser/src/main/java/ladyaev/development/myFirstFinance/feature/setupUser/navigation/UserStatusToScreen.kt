package ladyaev.development.myFirstFinance.feature.setupUser.navigation

import ladyaev.development.myFirstFinance.core.common.interfaces.Transformation
import ladyaev.development.myFirstFinance.core.ui.navigation.Screen
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ResidenceAddressScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.models.toUiModel
import ladyaev.development.myfirstfinance.domain.entities.Country
import ladyaev.development.myfirstfinance.domain.entities.UserStatus

internal class UserStatusToScreen(private val chosenCountry: Country?) : Transformation<UserStatus, Screen> {
    override fun map(data: UserStatus): Screen {
        return when (data) {
            UserStatus.NeedToSpecifyBirthDate -> Screen.SetupUser.BirthDate()
            UserStatus.NeedToSpecifyName -> Screen.SetupUser.Name()
            UserStatus.NeedToSpecifyEmail -> Screen.SetupUser.Email()
            UserStatus.NeedToSpecifyResidenceAddress -> {
                Screen.SetupUser.ResidenceAddress(
                    ResidenceAddressScreenArguments(chosenCountry = chosenCountry?.toUiModel())
                )
            }
            UserStatus.NeedToCreatePinCode -> Screen.SetupUser.CreatePinCode()
            UserStatus.NeedToSpecifyIdentityDocument -> Screen.SetupUser.CompleteRegistration()
            UserStatus.Registered -> Screen.SetupUser.CompleteRegistration()
        }
    }
}