package ladyaev.development.myFirstFinance.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ladyaev.development.myFirstFinance.core.common.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.ManageResources
import ladyaev.development.myFirstFinance.core.ui.error.HandleError
import ladyaev.development.myFirstFinance.domain.repository.misc.MiscRepository
import javax.inject.Singleton

@Module
class MiscModule {
    @Provides
    fun manageResources(context: Context): ManageResources = ManageResources.Base(context)

    @Provides
    fun handleError(manageResources: ManageResources): HandleError = HandleError.Base(manageResources)

    @Singleton
    @Provides
    fun countryCache(miscRepository: MiscRepository) = CountryCache(miscRepository)
}