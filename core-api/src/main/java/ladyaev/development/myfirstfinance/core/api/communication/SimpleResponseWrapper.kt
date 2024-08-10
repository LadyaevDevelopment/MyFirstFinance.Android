package ladyaev.development.myfirstfinance.core.api.communication

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.enums.OperationStatus

class SimpleResponseWrapper<TResponse> : ResponseWrapper<TResponse, Object?> {
	constructor() : super()

	constructor(status: OperationStatus, responseData: TResponse?, error: Object?, errorMessage: String?) : super(status, responseData, error, errorMessage)
}
