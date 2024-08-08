package ladyaev.development.myfirstfinance.domain.repositories.setupUser

import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeData
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.specifyBirthDate.SpecifyBirthDateError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError
import java.util.Date

interface SetupUserRepository {
    suspend fun requireConfirmationCode(
        phoneNumber: PhoneNumber
    ): OperationResult<RequireConfirmationCodeData, RequireConfirmationCodeError>

    suspend fun verifyConfirmationCode(
        codeId: String,
        code: String
    ): OperationResult<Unit, VerifyConfirmationCodeError>

    suspend fun specifyBirthDate(birthDate: Date): OperationResult<SpecifyUserInfoResult, SpecifyBirthDateError>

    suspend fun specifyName(
        lastName: String,
        firstName: String,
        middleName: String?
    ): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError>

    suspend fun specifyEmail(email: String): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError>

    suspend fun specifyPinCode(email: String): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError>

    suspend fun specifyResidenceAddress(
        countryIso2Code: String,
        city: String,
        street: String,
        buildingNumber: String,
        apartment: String
    ): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError>
}