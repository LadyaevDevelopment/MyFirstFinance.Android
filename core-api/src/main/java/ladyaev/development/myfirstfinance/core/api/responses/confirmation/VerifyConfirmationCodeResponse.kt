package ladyaev.development.myfirstfinance.core.api.responses.confirmation

import java.util.*
import java.math.*
import java.lang.*
import ladyaev.development.myfirstfinance.core.api.enums.UserStatus

class VerifyConfirmationCodeResponse {
	var userId: String? = null
	var accessToken: String = ""
	var userStatus: UserStatus = UserStatus.NEED_TO_SPECIFY_BIRTH_DATE

	constructor() : super()

	constructor(userId: String?, accessToken: String, userStatus: UserStatus) {
		this.userId = userId
		this.accessToken = accessToken
		this.userStatus = userStatus
	}
}
