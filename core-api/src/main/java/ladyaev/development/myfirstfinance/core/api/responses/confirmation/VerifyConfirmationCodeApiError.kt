package ladyaev.development.myfirstfinance.core.api.responses.confirmation

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.enums.VerifyConfirmationCodeApiErrorType

class VerifyConfirmationCodeApiError {
	var errorType: VerifyConfirmationCodeApiErrorType = VerifyConfirmationCodeApiErrorType.WRONG_CODE
	var temporaryBlockingTimeInSeconds: Int? = null

	constructor() : super()

	constructor(errorType: VerifyConfirmationCodeApiErrorType, temporaryBlockingTimeInSeconds: Int?) {
		this.errorType = errorType
		this.temporaryBlockingTimeInSeconds = temporaryBlockingTimeInSeconds
	}
}
