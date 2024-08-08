@file:Suppress("UnusedImport")

package ladyaev.development.myfirstfinance.core.api.apiclients.interfaces

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.communication.ResponseWrapper
import ladyaev.development.myfirstfinance.core.api.requests.confirmation.RequireConfirmationCodeRequest
import ladyaev.development.myfirstfinance.core.api.requests.confirmation.VerifyConfirmationCodeRequest
import ladyaev.development.myfirstfinance.core.api.responses.confirmation.RequireConfirmationCodeApiError
import ladyaev.development.myfirstfinance.core.api.responses.confirmation.RequireConfirmationCodeResponse
import ladyaev.development.myfirstfinance.core.api.responses.confirmation.VerifyConfirmationCodeApiError
import ladyaev.development.myfirstfinance.core.api.responses.confirmation.VerifyConfirmationCodeResponse

interface ConfirmationApiClient {
	suspend fun requireConfirmationCode(request: RequireConfirmationCodeRequest?, accessToken: String?): ResponseWrapper<RequireConfirmationCodeResponse?, RequireConfirmationCodeApiError?>

	fun requireConfirmationCode(request: RequireConfirmationCodeRequest?, accessToken: String?, callback: RequestCallback<ResponseWrapper<RequireConfirmationCodeResponse?, RequireConfirmationCodeApiError?>>?)

	suspend fun verifyConfirmationCode(request: VerifyConfirmationCodeRequest?, accessToken: String?): ResponseWrapper<VerifyConfirmationCodeResponse?, VerifyConfirmationCodeApiError?>

	fun verifyConfirmationCode(request: VerifyConfirmationCodeRequest?, accessToken: String?, callback: RequestCallback<ResponseWrapper<VerifyConfirmationCodeResponse?, VerifyConfirmationCodeApiError?>>?)
}
