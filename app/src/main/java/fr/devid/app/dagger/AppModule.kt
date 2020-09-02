package fr.devid.app.dagger

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import fr.devid.app.App
import fr.devid.app.BuildConfig
import fr.devid.app.Constants
import fr.devid.app.api.AppService
import fr.devid.app.room.AppDatabase
import fr.devid.app.services.AppServiceWrapper
import fr.devid.app.services.AuthenticationTokenInterceptor
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(includes = [ViewModelModule::class])
object AppModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideOkHttpClient(authenticationTokenInterceptor: AuthenticationTokenInterceptor): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(authenticationTokenInterceptor)
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }.build()

    @Singleton
    @Provides
    @JvmStatic
    fun provideMoshi(): Moshi = Moshi.Builder().apply {
        add(KotlinJsonAdapterFactory())
    }.build()

    @Singleton
    @Provides
    @JvmStatic
    fun provideAppService(okHttpClient: OkHttpClient, moshi: Moshi): AppService {
        val appService = Retrofit.Builder().apply {
            baseUrl(Constants.BASE_URL)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(okHttpClient)
        }.build().create(AppService::class.java)
        return AppServiceWrapper(appService)
    }

    @Provides
    @JvmStatic
    fun provideContext(app: App): Context = app

    @Singleton
    @Provides
    @JvmStatic
    fun provideAppDatabase(applicationContext: Context): AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app-db").build()
}
