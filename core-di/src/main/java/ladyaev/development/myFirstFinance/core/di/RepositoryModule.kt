package ladyaev.development.myFirstFinance.core.di

import dagger.Binds
import dagger.Module
import ladyaev.development.myFirstFinance.data.mock.MiscRepositoryMock
import ladyaev.development.myFirstFinance.data.mock.SetupUserRepositoryMock
import ladyaev.development.myFirstFinance.domain.repository.misc.MiscRepository
import ladyaev.development.myFirstFinance.domain.repository.setupUser.SetupUserRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun miscRepository(repository: MiscRepositoryMock): MiscRepository

    @Binds
    @Singleton
    fun setupUserRepository(repository: SetupUserRepositoryMock): SetupUserRepository
}