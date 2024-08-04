package ladyaev.development.myFirstFinance.feature.setupUser.screens.di

import dagger.Module
import dagger.Provides
import ladyaev.development.myFirstFinance.core.di.FeatureScope
import ladyaev.development.myFirstFinance.feature.setupUser.screens.misc.FeatureData

@Module
internal class MiscModule {
    @Provides
    @FeatureScope
    fun featureData() = FeatureData()
}