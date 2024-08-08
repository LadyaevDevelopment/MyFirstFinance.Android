package ladyaev.development.myfirstfinance.core.api.enums

import java.io.*
import com.google.gson.annotations.*
import ladyaev.development.myfirstfinance.core.api.R

enum class ApiUserStatus(val primitiveValue: Int) : Serializable {
	@SerializedName("needToSpecifyBirthDate")
	NEED_TO_SPECIFY_BIRTH_DATE(1) {
		override val displayNameResourceId = R.string.enum_apiUserStatus_needToSpecifyBirthDate
	},

	@SerializedName("needToSpecifyName")
	NEED_TO_SPECIFY_NAME(2) {
		override val displayNameResourceId = R.string.enum_apiUserStatus_needToSpecifyName
	},

	@SerializedName("needToSpecifyEmail")
	NEED_TO_SPECIFY_EMAIL(3) {
		override val displayNameResourceId = R.string.enum_apiUserStatus_needToSpecifyEmail
	},

	@SerializedName("needToSpecifyResidenceAddress")
	NEED_TO_SPECIFY_RESIDENCE_ADDRESS(4) {
		override val displayNameResourceId = R.string.enum_apiUserStatus_needToSpecifyResidenceAddress
	},

	@SerializedName("needToCreatePinCode")
	NEED_TO_CREATE_PIN_CODE(5) {
		override val displayNameResourceId = R.string.enum_apiUserStatus_needToCreatePinCode
	},

	@SerializedName("needToSpecifyIdentityDocument")
	NEED_TO_SPECIFY_IDENTITY_DOCUMENT(6) {
		override val displayNameResourceId = R.string.enum_apiUserStatus_needToSpecifyIdentityDocument
	},

	@SerializedName("registered")
	REGISTERED(7) {
		override val displayNameResourceId = R.string.enum_apiUserStatus_registered
	};

	abstract val displayNameResourceId: Int

	companion object {
		fun fromPrimitiveValue(primitiveValue: Int): ApiUserStatus? {
			return enumValues<ApiUserStatus>().firstOrNull { it.primitiveValue == primitiveValue }
		}
	}
}
