package ladyaev.development.myFirstFinance.core.ui.viewModel.operations

import kotlinx.coroutines.CoroutineScope
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.RequireConfirmationCode
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeResult

abstract class RequireConfirmationCodeOperation(
    private val scope: CoroutineScope,
    private val dispatchers: ManageDispatchers
) : ViewModelOperation.Complex<RequireConfirmationCodeOperation.Data, RequireConfirmationCodeResult>(), RequireConfirmationCode {

    override fun positiveCase(data: Data): Unit = with(data) {
        dispatchers.launchMain(scope) {
            onActivityChanged(true)
            val result = requireConfirmationCode(phoneNumber)
            onActivityChanged(false)
            when (result) {
                is OperationResult.StandardFailure -> {
                    onStandardError(result.error)
                }
                is OperationResult.SpecificFailure -> {
                    when (result.error) {
                        RequireConfirmationCodeError.UserBlocked -> {}
                        is RequireConfirmationCodeError.UserTemporaryBlocked -> {}
                        RequireConfirmationCodeError.InvalidData -> {}
                    }
                }
                is OperationResult.Success -> {
                    onSuccess(result.data)
                }
            }
        }
    }

    data class Data(val phoneNumber: PhoneNumber)
}