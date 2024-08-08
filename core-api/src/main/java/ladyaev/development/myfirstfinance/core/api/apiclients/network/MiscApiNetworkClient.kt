@file:Suppress("UnusedImport")

package ladyaev.development.myfirstfinance.core.api.apiclients.network

import java.util.*
import java.math.*
import java.lang.*
import java.text.*
import com.google.gson.reflect.*
import ladyaev.development.myfirstfinance.core.api.apiclients.interfaces.*
import ladyaev.development.myfirstfinance.core.api.apiclients.network.adapters.DateAdapter
import ladyaev.development.myfirstfinance.core.api.communication.SimpleResponseWrapper
import ladyaev.development.myfirstfinance.core.api.requests.misc.AddressesRequest
import ladyaev.development.myfirstfinance.core.api.responses.misc.AddressesResponse
import ladyaev.development.myfirstfinance.core.api.responses.misc.CountriesResponse
import ladyaev.development.myfirstfinance.core.api.responses.misc.PolicyDocumentsResponse

class MiscApiNetworkClient(configuration: ApiNetworkClientConfiguration) : BaseApiNetworkClient(configuration), MiscApiClient {
	override suspend fun policyDocuments(accessToken: String?): SimpleResponseWrapper<PolicyDocumentsResponse?> {
		val responseType = object : TypeToken<SimpleResponseWrapper<PolicyDocumentsResponse?>>() { }.type
		return makeRequest("GET", "Misc/PolicyDocuments", null, null, responseType, accessToken)
	}

	override fun policyDocuments(accessToken: String?, callback: RequestCallback<SimpleResponseWrapper<PolicyDocumentsResponse?>>?) {
		val responseType = object : TypeToken<SimpleResponseWrapper<PolicyDocumentsResponse?>>() { }.type
		return makeRequest("GET", "Misc/PolicyDocuments", null, null, responseType, callback, accessToken)
	}

	override suspend fun countries(accessToken: String?): SimpleResponseWrapper<CountriesResponse?> {
		val responseType = object : TypeToken<SimpleResponseWrapper<CountriesResponse?>>() { }.type
		return makeRequest("GET", "Misc/Countries", null, null, responseType, accessToken)
	}

	override fun countries(accessToken: String?, callback: RequestCallback<SimpleResponseWrapper<CountriesResponse?>>?) {
		val responseType = object : TypeToken<SimpleResponseWrapper<CountriesResponse?>>() { }.type
		return makeRequest("GET", "Misc/Countries", null, null, responseType, callback, accessToken)
	}

	override suspend fun addresses(request: AddressesRequest?, accessToken: String): SimpleResponseWrapper<AddressesResponse?> {
		val queryParameters = mutableListOf<Pair<String, String?>>().apply {
			add(Pair("CountryIso2Code", request?.countryIso2Code))
			add(Pair("SearchQuery", request?.searchQuery))
		}
		val responseType = object : TypeToken<SimpleResponseWrapper<AddressesResponse?>>() { }.type
		return makeRequest("GET", buildQueryAddress("Misc/Addresses", queryParameters), null, null, responseType, accessToken)
	}

	override fun addresses(request: AddressesRequest?, accessToken: String, callback: RequestCallback<SimpleResponseWrapper<AddressesResponse?>>?) {
		val queryParameters = mutableListOf<Pair<String, String?>>().apply {
			add(Pair("CountryIso2Code", request?.countryIso2Code))
			add(Pair("SearchQuery", request?.searchQuery))
		}
		val responseType = object : TypeToken<SimpleResponseWrapper<AddressesResponse?>>() { }.type
		return makeRequest("GET", buildQueryAddress("Misc/Addresses", queryParameters), null, null, responseType, callback, accessToken)
	}
}
