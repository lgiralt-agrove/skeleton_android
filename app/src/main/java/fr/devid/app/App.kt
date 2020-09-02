package fr.devid.app

import dagger.android.DaggerApplication
import fr.devid.app.dagger.DaggerAppComponent
import timber.log.Timber

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector() = DaggerAppComponent.factory().create(this)
}
