@file:Suppress("UnusedImport")

package ladyaev.development.myfirstfinance.core.api.apiclients.interfaces

import java.util.*
import java.math.*
import java.lang.*
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

interface RegistrationApiClient {
	suspend fun specifyBirthDate(request: SpecifyBirthDateRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyBirthDateApiError?>

	fun specifyBirthDate(request: SpecifyBirthDateRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyBirthDateApiError?>>?)

	suspend fun specifyName(request: SpecifyNameRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>

	fun specifyName(request: SpecifyNameRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?)

	suspend fun specifyEmail(request: SpecifyEmailRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>

	fun specifyEmail(request: SpecifyEmailRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?)

	suspend fun specifyResidenceAddress(request: SpecifyResidenceAddressRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>

	fun specifyResidenceAddress(request: SpecifyResidenceAddressRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?)

	suspend fun specifyPinCode(request: SpecifyPinCodeRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>

	fun specifyPinCode(request: SpecifyPinCodeRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?)

	suspend fun specifyIdentityDocument(request: SpecifyIdentityDocumentRequest?, accessToken: String): ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>

	fun specifyIdentityDocument(request: SpecifyIdentityDocumentRequest?, accessToken: String, callback: RequestCallback<ResponseWrapper<SpecifyUserDataResponse?, SpecifyUserInfoApiError?>>?)

	suspend fun skipProvisioningIdentityDocument(accessToken: String): SimpleResponseWrapper<SpecifyUserDataResponse?>

	fun skipProvisioningIdentityDocument(accessToken: String, callback: RequestCallback<SimpleResponseWrapper<SpecifyUserDataResponse?>>?)
}
