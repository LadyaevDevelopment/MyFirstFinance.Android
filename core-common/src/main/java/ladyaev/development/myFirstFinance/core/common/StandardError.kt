package ladyaev.development.myFirstFinance.core.common

sealed class StandardError {
    data object Connection : StandardError()
    data class Unknown(val errorMessage: String?) : StandardError()
    data class External(val errorMessage: String?) : StandardError()
}