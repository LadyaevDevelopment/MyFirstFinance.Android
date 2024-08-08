package ladyaev.development.myfirstfinance.core.api.responses.misc

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.models.CountryModel

class CountriesResponse {
	var countries: List<CountryModel> = listOf()

	constructor() : super()

	constructor(countries: List<CountryModel>) {
		this.countries = countries
	}
}
