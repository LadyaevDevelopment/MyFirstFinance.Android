package ladyaev.development.myfirstfinance.core.api.models

import java.util.*
import java.math.*
import java.lang.*

class AddressModel {
	var country: String = ""
	var city: String = ""
	var street: String = ""
	var buildingNumber: String = ""

	constructor() : super()

	constructor(country: String, city: String, street: String, buildingNumber: String) {
		this.country = country
		this.city = city
		this.street = street
		this.buildingNumber = buildingNumber
	}
}
