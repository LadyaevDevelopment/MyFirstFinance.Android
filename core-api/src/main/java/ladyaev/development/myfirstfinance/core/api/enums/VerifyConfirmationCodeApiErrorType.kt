package ladyaev.development.myfirstfinance.core.api.enums

import java.io.*
import com.google.gson.annotations.*
import ladyaev.development.myfirstfinance.core.api.R

enum class VerifyConfirmationCodeApiErrorType(val primitiveValue: Int) : Serializable {
	@SerializedName("wrongCode")
	WRONG_CODE(1) {
		override val displayNameResourceId = R.string.enum_verifyConfirmationCodeApiErrorType_wrongCode
	},

	@SerializedName("codeLifeTimeExpired")
	CODE_LIFE_TIME_EXPIRED(2) {
		override val displayNameResourceId = R.string.enum_verifyConfirmationCodeApiErrorType_codeLifeTimeExpired
	},

	@SerializedName("failedCodeConfirmationAttemptCountExceeded")
	FAILED_CODE_CONFIRMATION_ATTEMPT_COUNT_EXCEEDED(3) {
		override val displayNameResourceId = R.string.enum_verifyConfirmationCodeApiErrorType_failedCodeConfirmationAttemptCountExceeded
	};

	abstract val displayNameResourceId: Int

	companion object {
		fun fromPrimitiveValue(primitiveValue: Int): VerifyConfirmationCodeApiErrorType? {
			return enumValues<VerifyConfirmationCodeApiErrorType>().firstOrNull { it.primitiveValue == primitiveValue }
		}
	}
}
