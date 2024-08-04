package ladyaev.development.myFirstFinance.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ladyaev.development.myFirstFinance.core.common.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.ManageResources
import ladyaev.development.myFirstFinance.core.common.PhoneNumberValidation
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.domain.repository.misc.MiscRepository
import ladyaev.development.myFirstFinance.domain.repository.setupUser.SetupUserRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, MiscModule::class, PhoneNumberValidationModule::class])
interface AppComponent {

    val miscRepository: MiscRepository

    val setupUserRepository: SetupUserRepository

    val handleError: HandleError

    val phoneNumberValidation: PhoneNumberValidation

    val countryCache: CountryCache

    val manageDispatchers: ManageDispatchers

    val manageResources: ManageResources

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun withContext(context: Context): Builder
    }
}