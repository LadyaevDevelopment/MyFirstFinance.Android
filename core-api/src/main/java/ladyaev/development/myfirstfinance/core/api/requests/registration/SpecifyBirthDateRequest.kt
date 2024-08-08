package ladyaev.development.myfirstfinance.core.api.requests.registration

import java.util.*
import java.math.*
import java.lang.*

class SpecifyBirthDateRequest {
	var date: Date = Date()

	constructor() : super()

	constructor(date: Date) {
		this.date = date
	}
}
