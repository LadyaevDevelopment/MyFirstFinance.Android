@file:Suppress("UnusedImport")

package ladyaev.development.myfirstfinance.core.api.apiclients.network

import java.util.*
import java.math.*
import java.lang.*
import java.text.*
import com.google.gson.reflect.*
import ladyaev.development.myfirstfinance.core.api.apiclients.interfaces.*
import ladyaev.development.myfirstfinance.core.api.apiclients.network.adapters.DateAdapter
import ladyaev.development.myfirstfinance.core.api.communication.ResponseWrapper
import ladyaev.development.myfirstfinance.core.api.requests.confirmation.RequireConfirmationCodeRequest
import ladyaev.development.myfirstfinance.core.api.requests.confirmation.VerifyConfirmationCodeRequest
import ladyaev.development.myfirstfinance.core.api.responses.confirmation.RequireConfirmationCodeApiError
import ladyaev.development.myfirstfinance.core.api.responses.confirmation.RequireConfirmationCodeResponse
import ladyaev.development.myfirstfinance.core.api.responses.confirmation.VerifyConfirmationCodeApiError
import ladyaev.development.myfirstfinance.core.api.responses.confirmation.VerifyConfirmationCodeResponse

class ConfirmationApiNetworkClient(configuration: ApiNetworkClientConfiguration) : BaseApiNetworkClient(configuration), ConfirmationApiClient {
	override suspend fun requireConfirmationCode(request: RequireConfirmationCodeRequest?, accessToken: String?): ResponseWrapper<RequireConfirmationCodeResponse?, RequireConfirmationCodeApiError?> {
		val requestType = object : TypeToken<RequireConfirmationCodeRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<RequireConfirmationCodeResponse?, RequireConfirmationCodeApiError?>>() { }.type
		return makeRequest("POST", "Confirmation/RequireConfirmationCode", request, requestType, responseType, accessToken)
	}

	override fun requireConfirmationCode(request: RequireConfirmationCodeRequest?, accessToken: String?, callback: RequestCallback<ResponseWrapper<RequireConfirmationCodeResponse?, RequireConfirmationCodeApiError?>>?) {
		val requestType = object : TypeToken<RequireConfirmationCodeRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<RequireConfirmationCodeResponse?, RequireConfirmationCodeApiError?>>() { }.type
		return makeRequest("POST", "Confirmation/RequireConfirmationCode", request, requestType, responseType, callback, accessToken)
	}

	override suspend fun verifyConfirmationCode(request: VerifyConfirmationCodeRequest?, accessToken: String?): ResponseWrapper<VerifyConfirmationCodeResponse?, VerifyConfirmationCodeApiError?> {
		val requestType = object : TypeToken<VerifyConfirmationCodeRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<VerifyConfirmationCodeResponse?, VerifyConfirmationCodeApiError?>>() { }.type
		return makeRequest("POST", "Confirmation/VerifyConfirmationCode", request, requestType, responseType, accessToken)
	}

	override fun verifyConfirmationCode(request: VerifyConfirmationCodeRequest?, accessToken: String?, callback: RequestCallback<ResponseWrapper<VerifyConfirmationCodeResponse?, VerifyConfirmationCodeApiError?>>?) {
		val requestType = object : TypeToken<VerifyConfirmationCodeRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<VerifyConfirmationCodeResponse?, VerifyConfirmationCodeApiError?>>() { }.type
		return makeRequest("POST", "Confirmation/VerifyConfirmationCode", request, requestType, responseType, callback, accessToken)
	}
}
