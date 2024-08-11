package ladyaev.development.myfirstfinance.core.api.requests.registration

import java.util.*
import java.math.*
import java.lang.*

class SpecifyIdentityDocumentRequest {
	var documentBytes: ByteArray = ByteArray(0)

	constructor() : super()

	constructor(documentBytes: ByteArray) {
		this.documentBytes = documentBytes
	}
}
