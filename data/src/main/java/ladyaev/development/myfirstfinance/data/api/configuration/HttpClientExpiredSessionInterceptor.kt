package ladyaev.development.myfirstfinance.data.api.configuration

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpClientExpiredSessionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        var response = chain.proceed(request)
        //TODO: logic
        return response
    }
}