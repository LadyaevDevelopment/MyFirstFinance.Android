package ladyaev.development.myFirstFinance.android

import android.app.Application
import ladyaev.development.myFirstFinance.core.di.AppComponent
import ladyaev.development.myFirstFinance.core.di.DaggerAppComponent
import ladyaev.development.myFirstFinance.feature.setupUser.screens.di.DaggerSetupUserComponent
import ladyaev.development.myFirstFinance.feature.setupUser.screens.di.SetupUserComponent

class MainApplication : Application() {
    val appComponent: AppComponent by lazy  {
        DaggerAppComponent.builder()
            .withContext(this)
            .build()
    }
    val setupUserComponent: SetupUserComponent by lazy {
        DaggerSetupUserComponent.builder()
            .appComponent(appComponent)
            .build()
    }
}