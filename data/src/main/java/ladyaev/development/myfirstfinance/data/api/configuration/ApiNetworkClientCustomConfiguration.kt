package ladyaev.development.myfirstfinance.data.api.configuration

import com.chuckerteam.chucker.api.ChuckerInterceptor
import ladyaev.development.myFirstFinance.core.common.extensions.applyIf
import ladyaev.development.myfirstfinance.core.api.apiclients.network.ApiNetworkClientConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

open class ApiNetworkClientCustomConfiguration(
    private val apiUrl: String,
    private val enableLogging: Boolean,
    private val timeout: Long,
    private val timeoutUnit: TimeUnit,
    private val chuckerInterceptor: ChuckerInterceptor?,
    private val expiredSessionInterceptor: HttpClientExpiredSessionInterceptor?
) : ApiNetworkClientConfiguration {
    companion object {
        private val commonHttpClient = OkHttpClient()
    }

    override fun getRequestAddress(relativeAddress: String) =
        apiUrl.trimEnd('/') + "/" + relativeAddress.trimStart('/')

    override fun getHttpClient(): OkHttpClient {
        return commonHttpClient.newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(if (enableLogging) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .applyIf(expiredSessionInterceptor != null) {
                addInterceptor(expiredSessionInterceptor!!)
            }
            .applyIf(chuckerInterceptor != null) {
                addInterceptor(chuckerInterceptor!!)
            }
            .connectTimeout(timeout, timeoutUnit)
            .writeTimeout(timeout, timeoutUnit)
            .readTimeout(timeout, timeoutUnit)
            .build()
    }

    class Base(
        apiUrl: String,
        chuckerInterceptor: ChuckerInterceptor?,
    ) : ApiNetworkClientCustomConfiguration(
        apiUrl = apiUrl,
        enableLogging = true,
        timeout = 1000,
        timeoutUnit = TimeUnit.SECONDS,
        chuckerInterceptor = chuckerInterceptor,
        expiredSessionInterceptor = null
    )
}