package ladyaev.development.myFirstFinance.core.di

import dagger.Binds
import dagger.Module
import ladyaev.development.myFirstFinance.core.common.utils.CurrentDate

@Module
abstract class MiscBindsModule {
    @Binds
    abstract fun currentDate(currentDate: CurrentDate.Base): CurrentDate
}