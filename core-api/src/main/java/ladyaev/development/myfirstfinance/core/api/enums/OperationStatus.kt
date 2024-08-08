package ladyaev.development.myfirstfinance.core.api.enums

import java.io.*
import com.google.gson.annotations.*
import ladyaev.development.myfirstfinance.core.api.R

enum class OperationStatus(val primitiveValue: Int) : Serializable {
	@SerializedName("success")
	SUCCESS(1) {
		override val displayNameResourceId = R.string.enum_operationStatus_success
	},

	@SerializedName("failed")
	FAILED(2) {
		override val displayNameResourceId = R.string.enum_operationStatus_failed
	},

	@SerializedName("forbidden")
	FORBIDDEN(3) {
		override val displayNameResourceId = R.string.enum_operationStatus_forbidden
	},

	@SerializedName("invalidRequest")
	INVALID_REQUEST(4) {
		override val displayNameResourceId = R.string.enum_operationStatus_invalidRequest
	},

	@SerializedName("notFound")
	NOT_FOUND(5) {
		override val displayNameResourceId = R.string.enum_operationStatus_notFound
	},

	@SerializedName("unsupportedApiVersion")
	UNSUPPORTED_API_VERSION(6) {
		override val displayNameResourceId = R.string.enum_operationStatus_unsupportedApiVersion
	},

	@SerializedName("tooManyRequests")
	TOO_MANY_REQUESTS(7) {
		override val displayNameResourceId = R.string.enum_operationStatus_tooManyRequests
	};

	abstract val displayNameResourceId: Int

	companion object {
		fun fromPrimitiveValue(primitiveValue: Int): OperationStatus? {
			return enumValues<OperationStatus>().firstOrNull { it.primitiveValue == primitiveValue }
		}
	}
}
