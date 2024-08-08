package ladyaev.development.myfirstfinance.core.api.enums

import java.io.*
import com.google.gson.annotations.*
import ladyaev.development.myfirstfinance.core.api.R

enum class RequireConfirmationCodeApiErrorType(val primitiveValue: Int) : Serializable {
	@SerializedName("userBlocked")
	USER_BLOCKED(1) {
		override val displayNameResourceId = R.string.enum_requireConfirmationCodeApiErrorType_userBlocked
	},

	@SerializedName("userTemporaryBlocked")
	USER_TEMPORARY_BLOCKED(2) {
		override val displayNameResourceId = R.string.enum_requireConfirmationCodeApiErrorType_userTemporaryBlocked
	};

	abstract val displayNameResourceId: Int

	companion object {
		fun fromPrimitiveValue(primitiveValue: Int): RequireConfirmationCodeApiErrorType? {
			return enumValues<RequireConfirmationCodeApiErrorType>().firstOrNull { it.primitiveValue == primitiveValue }
		}
	}
}
