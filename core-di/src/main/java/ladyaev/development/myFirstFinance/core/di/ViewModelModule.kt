package ladyaev.development.myFirstFinance.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
//    @Binds
//    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(EmptyViewModel::class)
//    abstract fun loginViewModel(viewModel: EmptyViewModel): ViewModel
}