package ladyaev.development.myFirstFinance.core.ui.viewModel

interface ViewModelContract<InputData> {
    fun initialize(firstTime: Boolean, data: InputData) = run { }
}