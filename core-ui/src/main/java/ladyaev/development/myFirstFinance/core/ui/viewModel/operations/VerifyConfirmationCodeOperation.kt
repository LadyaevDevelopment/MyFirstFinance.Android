package ladyaev.development.myFirstFinance.core.ui.viewModel.operations

import kotlinx.coroutines.CoroutineScope
import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.VerifyConfirmationCode
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError

abstract class VerifyConfirmationCodeOperation(
    private val scope: CoroutineScope,
    private val dispatchers: ManageDispatchers
) : ViewModelOperation.Complex<VerifyConfirmationCodeOperation.Data, Unit>(), VerifyConfirmationCode {

    abstract fun onWrongCode()

    override fun positiveCase(data: Data): Unit = with(data) {
        dispatchers.launchMain(scope) {
            onActivityChanged(true)
            val result = verifyConfirmationCode(codeId, code)
            onActivityChanged(false)
            when (result) {
                is OperationResult.StandardFailure -> {
                    onStandardError(result.error)
                }
                is OperationResult.SpecificFailure -> {
                    when (result.error) {
                        is VerifyConfirmationCodeError.UserTemporaryBlocked -> {}
                        VerifyConfirmationCodeError.WrongCode -> {
                            onWrongCode()
                        }
                    }
                }
                is OperationResult.Success -> {
                    onSuccess(Unit)
                }
            }
        }
    }

    data class Data(val codeId: Id, val code: Code)
}