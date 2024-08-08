package ladyaev.development.myfirstfinance.core.api.apiclients.network

import okhttp3.OkHttpClient

interface ApiNetworkClientConfiguration {
	fun getRequestAddress(relativeAddress: String): String

	fun getHttpClient(): OkHttpClient
}
