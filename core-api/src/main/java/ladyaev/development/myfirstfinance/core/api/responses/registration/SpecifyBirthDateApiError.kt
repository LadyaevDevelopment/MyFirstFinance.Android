package ladyaev.development.myfirstfinance.core.api.responses.registration

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.enums.SpecifyBirthDateApiErrorType

class SpecifyBirthDateApiError {
	var errorType: SpecifyBirthDateApiErrorType = SpecifyBirthDateApiErrorType.USER_IS_MINOR

	constructor() : super()

	constructor(errorType: SpecifyBirthDateApiErrorType) {
		this.errorType = errorType
	}
}
