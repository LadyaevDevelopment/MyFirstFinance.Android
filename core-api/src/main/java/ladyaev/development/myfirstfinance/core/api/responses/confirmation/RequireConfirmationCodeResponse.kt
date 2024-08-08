package ladyaev.development.myfirstfinance.core.api.responses.confirmation

import java.util.*
import java.math.*
import java.lang.*

class RequireConfirmationCodeResponse {
	var confirmationCodeId: String? = null
	var confirmationCodeLength: Int = 0
	var resendTimeInSeconds: Int = 0
	var allowedCodeConfirmationAttemptCount: Int = 0
	var confirmationCodeLifeTimeInSeconds: Int = 0

	constructor() : super()

	constructor(confirmationCodeId: String?, confirmationCodeLength: Int, resendTimeInSeconds: Int, allowedCodeConfirmationAttemptCount: Int, confirmationCodeLifeTimeInSeconds: Int) {
		this.confirmationCodeId = confirmationCodeId
		this.confirmationCodeLength = confirmationCodeLength
		this.resendTimeInSeconds = resendTimeInSeconds
		this.allowedCodeConfirmationAttemptCount = allowedCodeConfirmationAttemptCount
		this.confirmationCodeLifeTimeInSeconds = confirmationCodeLifeTimeInSeconds
	}
}
