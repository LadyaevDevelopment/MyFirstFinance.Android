package ladyaev.development.myFirstFinance.core.ui.viewModel.operations

import ladyaev.development.myfirstfinance.domain.operation.StandardError

interface ViewModelOperation<TInput> {
    fun canProcess(): Boolean = true
    fun process(data: TInput)

    abstract class Abstract<TInput> : ViewModelOperation<TInput> {
        final override fun process(data: TInput) {
            if (canProcess()) {
                positiveCase(data)
            }
        }
        protected abstract fun positiveCase(data: TInput)
    }

    abstract class Complex<TInput, TResult> : Abstract<TInput>() {
        abstract fun onActivityChanged(isActive: Boolean)
        abstract fun onStandardError(error: StandardError)
        abstract fun onSuccess(data: TResult)
    }
}