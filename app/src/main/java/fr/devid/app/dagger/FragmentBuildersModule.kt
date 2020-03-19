package fr.devid.app.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fr.devid.app.ui.about.AboutFragment
import fr.devid.app.ui.home.HomeFragment
import fr.devid.app.ui.login.LoginFragment
import fr.devid.app.ui.main.MainFragment
import fr.devid.app.ui.register.RegisterFragment
import fr.devid.app.ui.splash.SplashFragment

@Module
interface FragmentBuildersModule {

    @ContributesAndroidInjector
    fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun contributeAboutFragment(): AboutFragment
}
