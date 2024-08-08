package ladyaev.development.myfirstfinance.core.api.requests.confirmation

import java.util.*
import java.math.*
import java.lang.*

class RequireConfirmationCodeRequest {
	var countryPhoneCode: String = ""
	var phoneNumber: String = ""

	constructor() : super()

	constructor(countryPhoneCode: String, phoneNumber: String) {
		this.countryPhoneCode = countryPhoneCode
		this.phoneNumber = phoneNumber
	}
}
