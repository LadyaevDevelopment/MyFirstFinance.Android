package ladyaev.development.myfirstfinance.core.api.requests.registration

import java.util.*
import java.math.*
import java.lang.*

class SpecifyNameRequest {
	var firstName: String = ""
	var lastName: String = ""
	var middleName: String? = null

	constructor() : super()

	constructor(firstName: String, lastName: String, middleName: String?) {
		this.firstName = firstName
		this.lastName = lastName
		this.middleName = middleName
	}
}
