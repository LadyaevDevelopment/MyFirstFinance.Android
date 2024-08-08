package ladyaev.development.myFirstFinance.feature.setupUser.di

import androidx.lifecycle.ViewModelProvider
import dagger.Component
import ladyaev.development.myFirstFinance.core.di.AppComponent
import ladyaev.development.myFirstFinance.core.di.FeatureScope

@Component(dependencies = [AppComponent::class], modules = [ViewModuleModule::class, MiscModule::class])
@FeatureScope
interface SetupUserComponent {

    val viewModelFactory: ViewModelProvider.Factory

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): SetupUserComponent
    }
}