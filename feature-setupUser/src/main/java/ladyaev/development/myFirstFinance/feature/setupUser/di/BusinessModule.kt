package ladyaev.development.myFirstFinance.feature.setupUser.di

import dagger.Module
import dagger.Provides
import ladyaev.development.myFirstFinance.core.common.utils.CurrentDate
import ladyaev.development.myFirstFinance.core.di.CountryCache
import ladyaev.development.myFirstFinance.core.di.FeatureScope
import ladyaev.development.myFirstFinance.feature.setupUser.business.ChooseCountryUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.FeatureData
import ladyaev.development.myFirstFinance.feature.setupUser.business.RequireChosenCountryUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.RequireConfirmationCodeUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.RequireCountriesUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.RequirePolicyDocumentsUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyBirthDateUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyEmailUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyNameUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyPinCodeUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.SpecifyResidenceAddressUseCase
import ladyaev.development.myFirstFinance.feature.setupUser.business.VerifyConfirmationCodeUseCase
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository

@Module
internal class BusinessModule {
    @Provides
    @FeatureScope
    fun featureData() = FeatureData()

    @Provides
    @FeatureScope
    fun chooseCountryUseCase(featureData: FeatureData) = ChooseCountryUseCase(featureData)

    @Provides
    @FeatureScope
    fun requireChosenCountryUseCase(featureData: FeatureData) = RequireChosenCountryUseCase(featureData)

    @Provides
    @FeatureScope
    fun requireConfirmationCodeUseCase(repository: SetupUserRepository) = RequireConfirmationCodeUseCase(repository)

    @Provides
    @FeatureScope
    fun requireCountriesUseCase(countryCache: CountryCache) = RequireCountriesUseCase(countryCache)

    @Provides
    @FeatureScope
    fun requirePolicyDocumentsUseCase(repository: MiscRepository) = RequirePolicyDocumentsUseCase(repository)

    @Provides
    @FeatureScope
    fun specifyBirthDateUseCase(repository: SetupUserRepository, currentDate: CurrentDate) = SpecifyBirthDateUseCase(currentDate, repository)

    @Provides
    @FeatureScope
    fun specifyEmailUseCase(repository: SetupUserRepository) = SpecifyEmailUseCase(repository)

    @Provides
    @FeatureScope
    fun specifyNameUseCase(repository: SetupUserRepository) = SpecifyNameUseCase(repository)

    @Provides
    @FeatureScope
    fun specifyPinCodeUseCase(repository: SetupUserRepository) = SpecifyPinCodeUseCase(repository)

    @Provides
    @FeatureScope
    fun specifyResidenceAddressUseCase(repository: SetupUserRepository) = SpecifyResidenceAddressUseCase(repository)

    @Provides
    @FeatureScope
    fun verifyConfirmationCodeUseCase(repository: SetupUserRepository) = VerifyConfirmationCodeUseCase(repository)
}