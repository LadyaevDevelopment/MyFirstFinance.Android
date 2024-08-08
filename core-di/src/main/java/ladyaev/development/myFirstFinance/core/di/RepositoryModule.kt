package ladyaev.development.myFirstFinance.core.di

import dagger.Binds
import dagger.Module
import ladyaev.development.myfirstfinance.data.mock.MiscRepositoryMock
import ladyaev.development.myfirstfinance.data.mock.SetupUserRepositoryMock
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun miscRepository(repository: MiscRepositoryMock): MiscRepository

    @Binds
    @Singleton
    fun setupUserRepository(repository: SetupUserRepositoryMock): ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
}