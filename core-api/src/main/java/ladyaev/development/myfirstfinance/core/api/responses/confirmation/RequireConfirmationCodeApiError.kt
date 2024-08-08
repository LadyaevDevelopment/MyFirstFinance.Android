package ladyaev.development.myfirstfinance.core.api.responses.confirmation

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.enums.RequireConfirmationCodeApiErrorType

class RequireConfirmationCodeApiError {
	var errorType: RequireConfirmationCodeApiErrorType = RequireConfirmationCodeApiErrorType.USER_BLOCKED
	var remainingTemporaryBlockingTimeInSeconds: Int? = null

	constructor() : super()

	constructor(errorType: RequireConfirmationCodeApiErrorType, remainingTemporaryBlockingTimeInSeconds: Int?) {
		this.errorType = errorType
		this.remainingTemporaryBlockingTimeInSeconds = remainingTemporaryBlockingTimeInSeconds
	}
}
