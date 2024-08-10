package ladyaev.development.myFirstFinance.core.ui.viewModel

interface ViewModelContract<TData> {
    fun initialize(firstTime: Boolean, data: TData) = run { }
}