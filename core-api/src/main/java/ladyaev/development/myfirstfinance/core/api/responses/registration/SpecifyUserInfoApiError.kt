package ladyaev.development.myfirstfinance.core.api.responses.registration

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.enums.SpecifyUserInfoApiErrorType

class SpecifyUserInfoApiError {
	var errorType: SpecifyUserInfoApiErrorType = SpecifyUserInfoApiErrorType.INVALID_DATA

	constructor() : super()

	constructor(errorType: SpecifyUserInfoApiErrorType) {
		this.errorType = errorType
	}
}
