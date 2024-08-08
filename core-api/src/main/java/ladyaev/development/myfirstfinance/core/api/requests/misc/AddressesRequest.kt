package ladyaev.development.myfirstfinance.core.api.requests.misc

import java.util.*
import java.math.*
import java.lang.*

class AddressesRequest {
	var countryIso2Code: String? = null
	var searchQuery: String? = null

	constructor() : super()

	constructor(countryIso2Code: String?, searchQuery: String?) {
		this.countryIso2Code = countryIso2Code
		this.searchQuery = searchQuery
	}
}
