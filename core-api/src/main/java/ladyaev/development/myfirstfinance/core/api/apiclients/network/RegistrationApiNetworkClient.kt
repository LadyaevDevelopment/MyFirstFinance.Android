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
import ladyaev.development.myfirstfinance.core.api.communication.SimpleResponseWrapper
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyBirthDateRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyEmailRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyIdentityDocumentRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyNameRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyPinCodeRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyResidenceAddressRequest
import ladyaev.development.myfirstfinance.core.api.responses.registration.SpecifyBirthDateApiError
import ladyaev.development.myfirstfinance.core.api.responses.registration.SpecifyUserDataResponse
import ladyaev.development.myfirstfinance.core.api.responses.registration.SpecifyUserInfoApiError

class RegistrationApiNetworkClient(configuration: ApiNetworkClientConfiguration) : BaseApiNetworkClient(configuration), RegistrationApiClient {
	override suspend fun specifyBirthDate(request: SpecifyBirthDateRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyBirthDateApiError?> {
		val requestType = object : TypeToken<SpecifyBirthDateRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyBirthDateApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyBirthDate", request, requestType, responseType, accessToken)
	}

	override fun specifyBirthDate(request: SpecifyBirthDateRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyBirthDateApiError?>>?) {
		val requestType = object : TypeToken<SpecifyBirthDateRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyBirthDateApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyBirthDate", request, requestType, responseType, callback, accessToken)
	}

	override suspend fun specifyName(request: SpecifyNameRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?> {
		val requestType = object : TypeToken<SpecifyNameRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyName", request, requestType, responseType, accessToken)
	}

	override fun specifyName(request: SpecifyNameRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?) {
		val requestType = object : TypeToken<SpecifyNameRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyName", request, requestType, responseType, callback, accessToken)
	}

	override suspend fun specifyEmail(request: SpecifyEmailRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?> {
		val requestType = object : TypeToken<SpecifyEmailRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyEmail", request, requestType, responseType, accessToken)
	}

	override fun specifyEmail(request: SpecifyEmailRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?) {
		val requestType = object : TypeToken<SpecifyEmailRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyEmail", request, requestType, responseType, callback, accessToken)
	}

	override suspend fun specifyResidenceAddress(request: SpecifyResidenceAddressRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?> {
		val requestType = object : TypeToken<SpecifyResidenceAddressRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyResidenceAddress", request, requestType, responseType, accessToken)
	}

	override fun specifyResidenceAddress(request: SpecifyResidenceAddressRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?) {
		val requestType = object : TypeToken<SpecifyResidenceAddressRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyResidenceAddress", request, requestType, responseType, callback, accessToken)
	}

	override suspend fun specifyPinCode(request: SpecifyPinCodeRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?> {
		val requestType = object : TypeToken<SpecifyPinCodeRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyPinCode", request, requestType, responseType, accessToken)
	}

	override fun specifyPinCode(request: SpecifyPinCodeRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?) {
		val requestType = object : TypeToken<SpecifyPinCodeRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyPinCode", request, requestType, responseType, callback, accessToken)
	}

	override suspend fun specifyIdentityDocument(request: SpecifyIdentityDocumentRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?> {
		val requestType = object : TypeToken<SpecifyIdentityDocumentRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyIdentityDocument", request, requestType, responseType, accessToken)
	}

	override fun specifyIdentityDocument(request: SpecifyIdentityDocumentRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?) {
		val requestType = object : TypeToken<SpecifyIdentityDocumentRequest>() { }.type
		val responseType = object : TypeToken<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>() { }.type
		return makeRequest("PATCH", "Registration/SpecifyIdentityDocument", request, requestType, responseType, callback, accessToken)
	}

	override suspend fun skipProvisioningIdentityDocument(accessToken: String): SimpleResponseWrapper<SpecifyUserDataResponse?> {
		val responseType = object : TypeToken<SimpleResponseWrapper<SpecifyUserDataResponse?>>() { }.type
		return makeRequest("PATCH", "Registration/SkipProvisioningIdentityDocument", null, null, responseType, accessToken)
	}

	override fun skipProvisioningIdentityDocument(accessToken: String, callback: RequestCallback<SimpleResponseWrapper<SpecifyUserDataResponse?>>?) {
		val responseType = object : TypeToken<SimpleResponseWrapper<SpecifyUserDataResponse?>>() { }.type
		return makeRequest("PATCH", "Registration/SkipProvisioningIdentityDocument", null, null, responseType, callback, accessToken)
	}
}
