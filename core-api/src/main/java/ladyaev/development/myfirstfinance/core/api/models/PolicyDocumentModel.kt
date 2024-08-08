package ladyaev.development.myfirstfinance.core.api.models

import java.util.*
import java.math.*
import java.lang.*

class PolicyDocumentModel {
	var title: String = ""
	var url: String = ""

	constructor() : super()

	constructor(title: String, url: String) {
		this.title = title
		this.url = url
	}
}
