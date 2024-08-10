package ladyaev.development.myfirstfinance.data.mock

import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myFirstFinance.core.common.misc.Email
import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myFirstFinance.core.common.misc.Length
import ladyaev.development.myFirstFinance.core.common.misc.Name
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myFirstFinance.core.common.misc.Seconds
import ladyaev.development.myfirstfinance.domain.entities.ResidenceAddress
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.specifyBirthDate.SpecifyBirthDateError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError
import java.util.Date
import javax.inject.Inject

class SetupUserRepositoryMock @Inject constructor() : SetupUserRepository {
    override suspend fun requireConfirmationCode(phoneNumber: PhoneNumber): OperationResult<RequireConfirmationCodeResult, RequireConfirmationCodeError> {
        delay(500)
        return OperationResult.Success(
            RequireConfirmationCodeResult(
                Length(5),
                Id("0"),
                Seconds(10)
            )
        )
    }

    override suspend fun verifyConfirmationCode(
        codeId: Id,
        code: Code
    ): OperationResult<Unit, VerifyConfirmationCodeError> {
        delay(500)
        return if (code.data == "11111") {
            OperationResult.SpecificFailure(VerifyConfirmationCodeError.WrongCode)
        } else {
            OperationResult.Success(Unit)
        }
    }

    override suspend fun specifyBirthDate(birthDate: Date): OperationResult<SpecifyUserInfoResult, SpecifyBirthDateError> {
        TODO("Not yet implemented")
    }

    override suspend fun specifyName(name: Name): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        TODO("Not yet implemented")
    }

    override suspend fun specifyEmail(email: Email): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        TODO("Not yet implemented")
    }

    override suspend fun specifyPinCode(pinCode: Code): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        TODO("Not yet implemented")
    }

    override suspend fun specifyResidenceAddress(residenceAddress: ResidenceAddress): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        TODO("Not yet implemented")
    }
}