package ladyaev.development.myFirstFinance.core.di

import dagger.Binds
import dagger.Module
import ladyaev.development.myFirstFinance.core.common.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.PhoneNumberValidation

@Module
interface PhoneNumberValidationModule {
    @Binds
    fun phoneNumberValidation(base: PhoneNumberValidation.Base): PhoneNumberValidation

    @Binds
    fun manageDispatchers(base: ManageDispatchers.Base): ManageDispatchers
}