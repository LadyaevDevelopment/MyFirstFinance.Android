package ladyaev.development.myFirstFinance.core.ui.viewModel.operations

import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey

abstract class HandleKeyOperation : ViewModelOperation.Abstract<HandleKeyOperation.Data>() {

    abstract fun onUpdateCode(code: String)
    abstract fun onCodeCompletelyEntered(code: String)

    override fun positiveCase(data: Data) = with(data) {
        when (key) {
            KeyboardButtonKey.Key0,
            KeyboardButtonKey.Key1,
            KeyboardButtonKey.Key2,
            KeyboardButtonKey.Key3,
            KeyboardButtonKey.Key4,
            KeyboardButtonKey.Key5,
            KeyboardButtonKey.Key6,
            KeyboardButtonKey.Key7,
            KeyboardButtonKey.Key8,
            KeyboardButtonKey.Key9 -> {
                if (enteredCode.length < codeLength) {
                    val newCode = enteredCode + key.string
                    onUpdateCode(newCode)
                    if (newCode.length == codeLength) {
                        onCodeCompletelyEntered(newCode)
                    }
                }
            }
            KeyboardButtonKey.Delete -> {
                if (enteredCode.isNotEmpty()) {
                    onUpdateCode(enteredCode.substring(0, enteredCode.lastIndex))
                }
            }
            KeyboardButtonKey.Fake -> {}
        }
    }

    data class Data(
        val key: KeyboardButtonKey,
        val enteredCode: String,
        val codeLength: Int
    )
}