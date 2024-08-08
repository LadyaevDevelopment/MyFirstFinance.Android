package ladyaev.development.myfirstfinance.core.api.responses.misc

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.models.PolicyDocumentModel

class PolicyDocumentsResponse {
	var documents: List<PolicyDocumentModel> = listOf()

	constructor() : super()

	constructor(documents: List<PolicyDocumentModel>) {
		this.documents = documents
	}
}
