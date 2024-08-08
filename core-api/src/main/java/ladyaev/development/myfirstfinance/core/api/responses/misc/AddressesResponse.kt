package ladyaev.development.myfirstfinance.core.api.responses.misc

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.models.AddressModel

class AddressesResponse {
	var addresses: List<AddressModel> = listOf()

	constructor() : super()

	constructor(addresses: List<AddressModel>) {
		this.addresses = addresses
	}
}
