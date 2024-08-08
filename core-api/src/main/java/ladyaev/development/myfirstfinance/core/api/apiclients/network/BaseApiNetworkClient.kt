package ladyaev.development.myfirstfinance.core.api.apiclients.network

import java.io.*
import java.math.*
import java.net.*
import java.util.*
import java.util.concurrent.*
import java.lang.reflect.*
import android.os.*
import kotlin.coroutines.*
import kotlin.reflect.full.*
import kotlinx.coroutines.*
import com.google.gson.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ladyaev.development.myfirstfinance.core.api.apiclients.network.adapters.*
import ladyaev.development.myfirstfinance.core.api.apiclients.network.other.*
import ladyaev.development.myfirstfinance.core.api.apiclients.interfaces.*

open class BaseApiNetworkClient(val configuration: ApiNetworkClientConfiguration) {
	companion object {
		private fun createSerializer() = GsonBuilder()
			.registerTypeHierarchyAdapter(Date::class.java, DateAdapter(DateAdapter.DateSerializationStrategy.ISO_8601))
			.registerTypeHierarchyAdapter(BigDecimal::class.java, DecimalAdapter(DecimalAdapter.DecimalSerializationStrategy.NUMBER))
			.registerTypeHierarchyAdapter(ByteArray::class.java, ByteArrayAdapter())
			.registerTypeAdapterFactory(EnumAdapterFactory(EnumAdapterFactory.EnumSerializationStrategy.STRINGS))
			.create()

		fun <T> serialize(value: T): String = createSerializer().toJson(value)

		fun <T> serialize(value: T, type: Type): String = createSerializer().toJson(value, type)

		fun <T> deserialize(s: String, type: Type): T = createSerializer().fromJson(s, type)
	}

	protected suspend fun <TRequest, TResponse> makeRequest(method: String, relativeUrl: String, request: TRequest?, requestType: Type?, responseType: Type, accessToken: String?): TResponse = withContext(Dispatchers.Default) {
		val networkRequest = prepareNetworkRequest(method, relativeUrl, request, requestType, accessToken)
		return@withContext suspendCoroutine<TResponse> { continuation ->
			configuration.getHttpClient().newCall(networkRequest).enqueue(object : Callback {
				override fun onFailure(call: Call, e: IOException) {
					continuation.resumeWithException(e)
				}

				override fun onResponse(call: Call, response: Response) {
					try {
						val result = processNetworkResponse<TResponse>(response, responseType)
						continuation.resume(result)
					} catch (ex: Exception) {
						continuation.resumeWithException(ex)
					}
				}
			})
		}
	}

	protected fun <TRequest, TResponse> makeRequest(method: String, relativeUrl: String, request: TRequest?, requestType: Type?, responseType: Type, callback: RequestCallback<TResponse>?, accessToken: String?) {
		val networkRequest = prepareNetworkRequest(method, relativeUrl, request, requestType, accessToken)
		configuration.getHttpClient().newCall(networkRequest).enqueue(object : Callback {
			override fun onFailure(call: Call, e: IOException) {
				executeCallbackOnMainThread(callback, null, e)
			}

			override fun onResponse(call: Call, response: Response) {
				try {
					val result = processNetworkResponse<TResponse>(response, responseType)
					executeCallbackOnMainThread(callback, result, null)
				} catch (ex: Exception) {
					executeCallbackOnMainThread(callback, null, ex)
				}
			}
		})
	}

	protected fun buildQueryAddress(pattern: String, queryParameters: List<Pair<String, String?>>): String {
		var queryString = ""
		val segments = pattern.split('/').toMutableList()
		for ((name, value) in queryParameters) {
			if (value == null) {
				continue
			}
			val segmentIndex = segments.indexOfFirst { it == "{${name}}" }
			if (segmentIndex != -1) {
				segments[segmentIndex] = URLEncoder.encode(value, "utf-8")
			} else {
				queryString += (if (queryString.isEmpty()) "?" else "&") + URLEncoder.encode(name, "utf-8") + "=" + URLEncoder.encode(value, "utf-8")
			}
		}
		return segments.joinToString("/") + queryString
	}

	private fun <TRequest> prepareNetworkRequest(method: String, relativeUrl: String, request: TRequest?, requestType: Type?, accessToken: String?): Request {
		val mediaType = "application/json; charset=utf-8".toMediaType()
		val requestBody = (if (request == null || requestType == null) "" else serialize(request, requestType)).toRequestBody(mediaType)
		var builder = Request.Builder()
			.url(configuration.getRequestAddress(relativeUrl))
			.method(method, if (method == "GET" || method == "DELETE") null else requestBody)
			.cacheControl(CacheControl.FORCE_NETWORK)
		if (accessToken != null) {
			builder = builder.addHeader("Authorization", "Bearer $accessToken")
		}
		return builder.build()
	}

	private fun <TResponse> processNetworkResponse(response: Response, responseType: Type): TResponse {
		val responseBody = response.body?.string() ?: ""
		try {
			return deserialize(responseBody, responseType) ?: throw IllegalArgumentException("Can not deserialize response body")
		}
		catch (ex: Exception) {
			if (!response.isSuccessful) {
				throw HttpRequestException(response.request.url.toString(), response.code, responseBody)
			}
			throw ex
		}
	}

	private fun <TResponse> executeCallbackOnMainThread(callback: RequestCallback<TResponse>?, response: TResponse?, ex: Exception?) {
		if (callback != null) {
			Handler(Looper.getMainLooper()).post { callback.handle(response, ex) }
		}
	}
}
