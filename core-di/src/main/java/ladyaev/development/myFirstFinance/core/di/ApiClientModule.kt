package ladyaev.development.myFirstFinance.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import ladyaev.development.myfirstfinance.core.api.apiclients.interfaces.ConfirmationApiClient
import ladyaev.development.myfirstfinance.core.api.apiclients.interfaces.MiscApiClient
import ladyaev.development.myfirstfinance.core.api.apiclients.interfaces.RegistrationApiClient
import ladyaev.development.myfirstfinance.core.api.apiclients.network.ApiNetworkClientConfiguration
import ladyaev.development.myfirstfinance.core.api.apiclients.network.ConfirmationApiNetworkClient
import ladyaev.development.myfirstfinance.core.api.apiclients.network.MiscApiNetworkClient
import ladyaev.development.myfirstfinance.core.api.apiclients.network.RegistrationApiNetworkClient
import ladyaev.development.myfirstfinance.data.api.configuration.ApiNetworkClientCustomConfiguration
import ladyaev.development.myfirstfinance.data.api.configuration.NetworkEnvironment
import ladyaev.development.myfirstfinance.data.api.configuration.configuredChuckerInterceptor
import javax.inject.Singleton

@Module
class ApiClientModule {
    @Provides
    @Singleton
    fun networkEnvironment(): NetworkEnvironment = NetworkEnvironment.Base()

    @Provides
    @Singleton
    fun apiClientConfiguration(
        networkEnvironment: NetworkEnvironment,
        chuckerInterceptor: ChuckerInterceptor
    ): ApiNetworkClientConfiguration = ApiNetworkClientCustomConfiguration.Base(
        apiUrl = networkEnvironment.baseApiUrl,
        chuckerInterceptor = chuckerInterceptor
    )

    @Provides
    @Singleton
    fun chuckerInterceptor(context: Context): ChuckerInterceptor = configuredChuckerInterceptor(context)

    @Provides
    @Singleton
    fun miscApiClient(configuration: ApiNetworkClientConfiguration): MiscApiClient = MiscApiNetworkClient(configuration)

    @Provides
    @Singleton
    fun registrationApiClient(configuration: ApiNetworkClientConfiguration): RegistrationApiClient = RegistrationApiNetworkClient(configuration)

    @Provides
    @Singleton
    fun confirmationApiClient(configuration: ApiNetworkClientConfiguration): ConfirmationApiClient = ConfirmationApiNetworkClient(configuration)
}