package ladyaev.development.myfirstfinance.core.api.enums

import java.io.*
import com.google.gson.annotations.*
import ladyaev.development.myfirstfinance.core.api.R

enum class SpecifyBirthDateApiErrorType(val primitiveValue: Int) : Serializable {
	@SerializedName("userIsMinor")
	USER_IS_MINOR(1) {
		override val displayNameResourceId = R.string.enum_specifyBirthDateApiErrorType_userIsMinor
	},

	@SerializedName("invalidData")
	INVALID_DATA(2) {
		override val displayNameResourceId = R.string.enum_specifyBirthDateApiErrorType_invalidData
	};

	abstract val displayNameResourceId: Int

	companion object {
		fun fromPrimitiveValue(primitiveValue: Int): SpecifyBirthDateApiErrorType? {
			return enumValues<SpecifyBirthDateApiErrorType>().firstOrNull { it.primitiveValue == primitiveValue }
		}
	}
}
