package ladyaev.development.myfirstfinance.core.api.communication

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.enums.OperationStatus

open class ResponseWrapper<TResponse, TError> {
	var status: OperationStatus = OperationStatus.SUCCESS
	var responseData: TResponse? = null
	var error: TError? = null
	var errorMessage: String? = null

	constructor() : super()

	constructor(status: OperationStatus, responseData: TResponse?, error: TError?, errorMessage: String?) {
		this.status = status
		this.responseData = responseData
		this.error = error
		this.errorMessage = errorMessage
	}
}
