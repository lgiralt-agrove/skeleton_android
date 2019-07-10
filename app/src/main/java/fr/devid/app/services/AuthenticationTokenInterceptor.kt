package fr.devid.app.services

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationTokenInterceptor @Inject constructor(
    private val sharedPreferencesService: SharedPreferencesService
): Interceptor {

    companion object {
        const val AUTHENTICATION_HEADER = "Authentication: dummy"
        private const val AUTHENTICATION_HEADER_NAME = "Authentication"
    }

    var token: String?
        get() = _token
        set(value) {
            _token = value
            sharedPreferencesService.token = value
        }

    private var _token: String?

    init {
        _token = sharedPreferencesService.token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!request.header(AUTHENTICATION_HEADER_NAME).isNullOrEmpty()) {
            token?.let {
                request = request.newBuilder().header(AUTHENTICATION_HEADER_NAME, it).build()
            }
        }

        return chain.proceed(request)
    }
}