package ladyaev.development.myFirstFinance.core.common

sealed class OperationResult<out TResult, out TSpecificError> {
    data class Success<TResult>(val data: TResult) : OperationResult<TResult, Nothing>()
    data class StandardFailure(val error: StandardError) : OperationResult<Nothing, Nothing>()
    data class SpecificFailure<TSpecificError>(val error: TSpecificError) : OperationResult<Nothing, TSpecificError>()
}