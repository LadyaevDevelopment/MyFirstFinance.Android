package ladyaev.development.myfirstfinance.core.api.apiclients.network.other

@Suppress("MemberVisibilityCanBePrivate")
class HttpRequestException(val url: String, val statusCode: Int, val response: String) : Exception() {
	override val message: String
		get() = "Request to address $url completed with status code $statusCode"
}
