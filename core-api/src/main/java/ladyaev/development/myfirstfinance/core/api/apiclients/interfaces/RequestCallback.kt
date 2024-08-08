package ladyaev.development.myfirstfinance.core.api.apiclients.interfaces

fun interface RequestCallback<TResponse> {
    fun handle(response: TResponse?, exception: Exception?)
}
