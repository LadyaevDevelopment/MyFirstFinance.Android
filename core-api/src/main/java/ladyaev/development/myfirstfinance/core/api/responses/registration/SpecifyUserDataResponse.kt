package ladyaev.development.myfirstfinance.core.api.responses.registration

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.enums.ApiUserStatus

class SpecifyUserDataResponse {
	var userStatus: ApiUserStatus = ApiUserStatus.NEED_TO_SPECIFY_BIRTH_DATE
	var pinCodeLength: Int? = null

	constructor() : super()

	constructor(userStatus: ApiUserStatus, pinCodeLength: Int?) {
		this.userStatus = userStatus
		this.pinCodeLength = pinCodeLength
	}
}
