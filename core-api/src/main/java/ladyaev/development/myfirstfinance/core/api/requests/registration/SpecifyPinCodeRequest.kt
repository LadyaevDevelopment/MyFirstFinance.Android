package ladyaev.development.myfirstfinance.core.api.requests.registration

import java.util.*
import java.math.*
import java.lang.*

class SpecifyPinCodeRequest {
	var pinCode: String? = null

	constructor() : super()

	constructor(pinCode: String?) {
		this.pinCode = pinCode
	}
}
