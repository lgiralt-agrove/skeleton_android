package fr.devid.app.services

import fr.devid.app.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

class AppServiceWrapper(private val appService: AppService) : AppService {

    override suspend fun login(loginDto: LoginDto): Response<LoginResponseDto>? =
        ioContextExecutor { appService.login(loginDto) }

    override suspend fun register(registerDto: RegisterDto): Response<TokenDto>? =
        ioContextExecutor { appService.register(registerDto) }

    override suspend fun getProfile(): ProfileDto? =
        ioContextExecutor { appService.getProfile() }

    private suspend fun <T> ioContextExecutor(block: suspend () -> T): T? = withContext(Dispatchers.IO) {
        try {
            block()
        } catch (ex: Exception) {
            Timber.e(ex)
            null
        }
    }
}
