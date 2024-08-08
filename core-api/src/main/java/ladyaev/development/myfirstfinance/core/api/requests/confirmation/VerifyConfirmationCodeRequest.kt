package ladyaev.development.myfirstfinance.core.api.requests.confirmation

import java.util.*
import java.math.*
import java.lang.*

class VerifyConfirmationCodeRequest {
	var confirmationCodeId: String? = null
	var confirmationCode: String = ""

	constructor() : super()

	constructor(confirmationCodeId: String?, confirmationCode: String) {
		this.confirmationCodeId = confirmationCodeId
		this.confirmationCode = confirmationCode
	}
}
