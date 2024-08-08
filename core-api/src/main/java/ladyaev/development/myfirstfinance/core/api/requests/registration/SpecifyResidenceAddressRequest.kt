package ladyaev.development.myfirstfinance.core.api.requests.registration

import java.util.*
import java.math.*
import java.lang.*

class SpecifyResidenceAddressRequest {
	var countryId: String? = null
	var city: String? = null
	var street: String? = null
	var buildingNumber: String? = null
	var apartmentNumber: String? = null

	constructor() : super()

	constructor(countryId: String?, city: String?, street: String?, buildingNumber: String?, apartmentNumber: String?) {
		this.countryId = countryId
		this.city = city
		this.street = street
		this.buildingNumber = buildingNumber
		this.apartmentNumber = apartmentNumber
	}
}
