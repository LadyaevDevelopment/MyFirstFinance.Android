package ladyaev.development.myfirstfinance.core.api.models

import java.util.*
import java.math.*
import java.lang.*

class CountryModel {
	var id: String = ""
	var name: String = ""
	var phoneNumberCode: String = ""
	var flagImageUrl: String = ""
	var phoneNumberMasks: List<String> = listOf()

	constructor() : super()

	constructor(id: String, name: String, phoneNumberCode: String, flagImageUrl: String, phoneNumberMasks: List<String>) {
		this.id = id
		this.name = name
		this.phoneNumberCode = phoneNumberCode
		this.flagImageUrl = flagImageUrl
		this.phoneNumberMasks = phoneNumberMasks
	}
}
