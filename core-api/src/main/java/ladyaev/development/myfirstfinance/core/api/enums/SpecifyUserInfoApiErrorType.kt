package ladyaev.development.myfirstfinance.core.api.enums

import java.io.*
import com.google.gson.annotations.*
import ladyaev.development.myfirstfinance.core.api.R

enum class SpecifyUserInfoApiErrorType(val primitiveValue: Int) : Serializable {
	@SerializedName("invalidData")
	INVALID_DATA(1) {
		override val displayNameResourceId = R.string.enum_specifyUserInfoApiErrorType_invalidData
	};

	abstract val displayNameResourceId: Int

	companion object {
		fun fromPrimitiveValue(primitiveValue: Int): SpecifyUserInfoApiErrorType? {
			return enumValues<SpecifyUserInfoApiErrorType>().firstOrNull { it.primitiveValue == primitiveValue }
		}
	}
}
