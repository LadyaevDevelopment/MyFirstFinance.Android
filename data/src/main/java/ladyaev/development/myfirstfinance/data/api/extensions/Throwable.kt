package ladyaev.development.myfirstfinance.data.api.extensions

import ladyaev.development.myfirstfinance.core.api.apiclients.network.other.HttpRequestException
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.commonError(): StandardError {
    return when (this) {
        is UnknownHostException, is ConnectException -> StandardError.Connection
        is HttpRequestException -> StandardError.External(this.message)
        else -> StandardError.Unknown(this.message)
    }
}