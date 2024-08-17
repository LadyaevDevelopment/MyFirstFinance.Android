package ladyaev.development.myFirstFinance.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ladyaev.development.myFirstFinance.core.common.utils.CurrentDate
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.utils.ManageResources
import ladyaev.development.myFirstFinance.core.common.utils.PhoneNumberValidation
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RepositoryModule::class,
    MiscProvidesModule::class,
    PhoneNumberValidationModule::class,
    MiscBindsModule::class,
    ApiClientModule::class
])
interface AppComponent {

    val miscRepository: MiscRepository

    val setupUserRepository: SetupUserRepository

    val handleError: HandleError

    val phoneNumberValidation: PhoneNumberValidation

    val countryCache: CountryCache

    val dispatchers: ManageDispatchers

    val manageResources: ManageResources

    val currentDate: CurrentDate

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun withContext(context: Context): Builder
    }
}