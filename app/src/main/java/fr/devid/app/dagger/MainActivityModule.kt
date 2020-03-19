package fr.devid.app.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fr.devid.app.MainActivity

@Module
interface MainActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    fun contributeMainActivity(): MainActivity
}
