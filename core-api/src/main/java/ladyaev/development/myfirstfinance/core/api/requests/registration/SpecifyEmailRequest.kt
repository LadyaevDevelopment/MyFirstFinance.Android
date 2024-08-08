package ladyaev.development.myfirstfinance.core.api.requests.registration

import java.util.*
import java.math.*
import java.lang.*

class SpecifyEmailRequest {
	var email: String = ""

	constructor() : super()

	constructor(email: String) {
		this.email = email
	}
}
