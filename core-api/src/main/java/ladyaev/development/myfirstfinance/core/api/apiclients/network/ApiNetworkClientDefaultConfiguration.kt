package ladyaev.development.myfirstfinance.core.api.apiclients.network

import okhttp3.*
import okhttp3.logging.*
import java.util.concurrent.*

@Suppress("MemberVisibilityCanBePrivate")
class ApiNetworkClientDefaultConfiguration(val apiUrl: String, val enableLogging: Boolean, val timeout: Long = 60, val timeoutUnit: TimeUnit = TimeUnit.SECONDS): ApiNetworkClientConfiguration {
	companion object {
		private val commonHttpClient = OkHttpClient()
	}

	override fun getRequestAddress(relativeAddress: String) =
		apiUrl.trimEnd('/') + "/" + relativeAddress.trimStart('/')

	override fun getHttpClient(): OkHttpClient {
		return commonHttpClient.newBuilder()
			.addInterceptor(HttpLoggingInterceptor().setLevel(if (enableLogging) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
			.connectTimeout(timeout, timeoutUnit)
			.writeTimeout(timeout, timeoutUnit)
			.readTimeout(timeout, timeoutUnit)
			.build()
	}
}
