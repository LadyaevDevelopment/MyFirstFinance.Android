package ladyaev.development.myFirstFinance.android.di

import dagger.Component
import ladyaev.development.myFirstFinance.android.MainActivity
import ladyaev.development.myFirstFinance.core.di.AppComponent

@MainActivityScope
@Component(dependencies = [AppComponent::class])
interface MainActivityComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): MainActivityComponent
    }

    fun inject(activity: MainActivity)
}