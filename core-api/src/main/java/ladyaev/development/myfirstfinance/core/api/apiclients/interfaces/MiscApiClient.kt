@file:Suppress("UnusedImport")

package ladyaev.development.myfirstfinance.core.api.apiclients.interfaces

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.communication.SimpleResponseWrapper
import ladyaev.development.myfirstfinance.core.api.requests.misc.AddressesRequest
import ladyaev.development.myfirstfinance.core.api.responses.misc.AddressesResponse
import ladyaev.development.myfirstfinance.core.api.responses.misc.CountriesResponse
import ladyaev.development.myfirstfinance.core.api.responses.misc.PolicyDocumentsResponse

interface MiscApiClient {
	suspend fun policyDocuments(accessToken: String?): SimpleResponseWrapper<PolicyDocumentsResponse?>

	fun policyDocuments(accessToken: String?, callback: RequestCallback<SimpleResponseWrapper<PolicyDocumentsResponse?>>?)

	suspend fun countries(accessToken: String?): SimpleResponseWrapper<CountriesResponse?>

	fun countries(accessToken: String?, callback: RequestCallback<SimpleResponseWrapper<CountriesResponse?>>?)

	suspend fun addresses(request: AddressesRequest?, accessToken: String): SimpleResponseWrapper<AddressesResponse?>

	fun addresses(request: AddressesRequest?, accessToken: String, callback: RequestCallback<SimpleResponseWrapper<AddressesResponse?>>?)
}
