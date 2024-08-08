package ladyaev.development.myFirstFinance.feature.setupUser.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ladyaev.development.myFirstFinance.core.di.ViewModelFactory
import ladyaev.development.myFirstFinance.core.di.ViewModelKey
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.birthDate.BirthDateViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.chooseCountry.ChooseCountryViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.completeRegistration.CompleteRegistrationViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmPinCodeScreen.ConfirmPinCodeViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmationCode.ConfirmationCodeViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.createPinCode.CreatePinCodeViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.email.EmailViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.name.NameViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.phoneNumber.PhoneNumberViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.residenceAddress.ResidenceAddressViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.splash.SplashScreenViewModel
import ladyaev.development.myFirstFinance.feature.setupUser.presentation.startMenu.StartMenuViewModel

@Module
internal abstract class ViewModuleModule {
    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(StartMenuViewModel.Base::class)
    abstract fun startMenuViewModel(viewModel: StartMenuViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel.Base::class)
    abstract fun splashScreenViewModel(viewModel: SplashScreenViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PhoneNumberViewModel.Base::class)
    abstract fun phoneNumberViewModel(viewModel: PhoneNumberViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChooseCountryViewModel.Base::class)
    abstract fun chooseCountryViewModel(viewModel: ChooseCountryViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmationCodeViewModel.Base::class)
    abstract fun confirmationCodeViewModel(viewModel: ConfirmationCodeViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreatePinCodeViewModel.Base::class)
    abstract fun createPinCodeViewModel(viewModel: CreatePinCodeViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmPinCodeViewModel.Base::class)
    abstract fun confirmPinCodeViewModel(viewModel: ConfirmPinCodeViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BirthDateViewModel.Base::class)
    abstract fun birthDateViewModel(viewModel: BirthDateViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NameViewModel.Base::class)
    abstract fun nameViewModel(viewModel: NameViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmailViewModel.Base::class)
    abstract fun emailViewModel(viewModel: EmailViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResidenceAddressViewModel.Base::class)
    abstract fun residenceAddressViewModel(viewModel: ResidenceAddressViewModel.Base): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CompleteRegistrationViewModel.Base::class)
    abstract fun completeRegistrationViewModel(viewModel: CompleteRegistrationViewModel.Base): ViewModel
}