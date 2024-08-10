package ladyaev.development.myfirstfinance.domain.repositories.setupUser

import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myFirstFinance.core.common.misc.Email
import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myFirstFinance.core.common.misc.Name
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myfirstfinance.domain.entities.ResidenceAddress
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.specifyBirthDate.SpecifyBirthDateError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError
import java.util.Date

interface SetupUserRepository {
    suspend fun requireConfirmationCode(phoneNumber: PhoneNumber): OperationResult<RequireConfirmationCodeResult, RequireConfirmationCodeError>

    suspend fun verifyConfirmationCode(codeId: Id, code: Code): OperationResult<Unit, VerifyConfirmationCodeError>

    suspend fun specifyBirthDate(birthDate: Date): OperationResult<SpecifyUserInfoResult, SpecifyBirthDateError>

    suspend fun specifyName(name: Name): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError>

    suspend fun specifyEmail(email: Email): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError>

    suspend fun specifyPinCode(pinCode: Code): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError>

    suspend fun specifyResidenceAddress(residenceAddress: ResidenceAddress): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError>
}