package ladyaev.development.myfirstfinance.data.api.repositories

import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myFirstFinance.core.common.misc.Email
import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myFirstFinance.core.common.misc.Length
import ladyaev.development.myFirstFinance.core.common.misc.Name
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myFirstFinance.core.common.misc.Seconds
import ladyaev.development.myFirstFinance.core.common.utils.ManageDispatchers
import ladyaev.development.myFirstFinance.core.common.utils.UserData
import ladyaev.development.myfirstfinance.core.api.apiclients.interfaces.ConfirmationApiClient
import ladyaev.development.myfirstfinance.core.api.apiclients.interfaces.RegistrationApiClient
import ladyaev.development.myfirstfinance.core.api.requests.confirmation.RequireConfirmationCodeRequest
import ladyaev.development.myfirstfinance.core.api.requests.confirmation.VerifyConfirmationCodeRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyBirthDateRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyEmailRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyNameRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyPinCodeRequest
import ladyaev.development.myfirstfinance.core.api.requests.registration.SpecifyResidenceAddressRequest
import ladyaev.development.myfirstfinance.data.api.extensions.commonError
import ladyaev.development.myfirstfinance.data.api.toDomain
import ladyaev.development.myfirstfinance.domain.entities.ResidenceAddress
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.specifyBirthDate.SpecifyBirthDateError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError
import java.util.Date
import javax.inject.Inject

class SetupUserRepositoryRemote @Inject constructor(
    private val registrationApiClient: RegistrationApiClient,
    private val confirmationClient: ConfirmationApiClient,
    private val userData: UserData,
    private val dispatchers: ManageDispatchers
) : SetupUserRepository {
    override suspend fun requireConfirmationCode(phoneNumber: PhoneNumber): OperationResult<RequireConfirmationCodeResult, RequireConfirmationCodeError> {
        return dispatchers.withIO {
            try {
                val request = RequireConfirmationCodeRequest(
                    countryPhoneCode = phoneNumber.countryCode,
                    phoneNumber = phoneNumber.number
                )
                val response = confirmationClient.requireConfirmationCode(request, userData.accessToken)
                val result = response.responseData
                if (result != null) {
                    OperationResult.Success(
                        RequireConfirmationCodeResult(
                            codeLength = Length(result.confirmationCodeLength),
                            codeId = Id(result.confirmationCodeId),
                            resendingTimeInterval = Seconds(result.resendTimeInSeconds)))
                } else {
                    OperationResult.StandardFailure(StandardError.Unknown(null))
                }
            } catch (ex: Exception) {
                OperationResult.StandardFailure(ex.commonError())
            }
        }
    }

    override suspend fun verifyConfirmationCode(
        codeId: Id,
        code: Code
    ): OperationResult<Unit, VerifyConfirmationCodeError> {
        return dispatchers.withIO {
            try {
                val request = VerifyConfirmationCodeRequest(
                    confirmationCodeId = codeId.data,
                    confirmationCode = code.data
                )
                val response = confirmationClient.verifyConfirmationCode(request, userData.accessToken)
                val result = response.responseData
                if (result != null) {
                    OperationResult.Success(Unit)
                } else {
                    OperationResult.StandardFailure(StandardError.Unknown(null))
                }
            } catch (ex: Exception) {
                OperationResult.StandardFailure(ex.commonError())
            }
        }
    }

    override suspend fun specifyBirthDate(birthDate: Date): OperationResult<SpecifyUserInfoResult, SpecifyBirthDateError> {
        return dispatchers.withIO {
            try {
                val request = SpecifyBirthDateRequest(birthDate)
                val response = registrationApiClient.specifyBirthDate(request, userData.accessToken)
                val result = response.responseData
                if (result != null) {
                    OperationResult.Success(
                        SpecifyUserInfoResult(
                            userStatus = result.userStatus.toDomain(),
                            pinCodeLength = result.pinCodeLength?.let { Length(it) }
                        )
                    )
                } else {
                    OperationResult.StandardFailure(StandardError.Unknown(null))
                }
            } catch (ex: Exception) {
                OperationResult.StandardFailure(ex.commonError())
            }
        }
    }

    override suspend fun specifyName(name: Name): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        return dispatchers.withIO {
            try {
                val request = SpecifyNameRequest(
                    lastName = name.lastName,
                    firstName = name.firstName,
                    middleName = name.middleName
                )
                val response = registrationApiClient.specifyName(request, userData.accessToken)
                val result = response.responseData
                if (result != null) {
                    OperationResult.Success(
                        SpecifyUserInfoResult(
                            userStatus = result.userStatus.toDomain(),
                            pinCodeLength = result.pinCodeLength?.let { Length(it) }
                        )
                    )
                } else {
                    OperationResult.StandardFailure(StandardError.Unknown(null))
                }
            } catch (ex: Exception) {
                OperationResult.StandardFailure(ex.commonError())
            }
        }
    }

    override suspend fun specifyEmail(email: Email): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        return dispatchers.withIO {
            try {
                val request = SpecifyEmailRequest(email.data)
                val response = registrationApiClient.specifyEmail(request, userData.accessToken)
                val result = response.responseData
                if (result != null) {
                    OperationResult.Success(
                        SpecifyUserInfoResult(
                            userStatus = result.userStatus.toDomain(),
                            pinCodeLength = result.pinCodeLength?.let { Length(it) }
                        )
                    )
                } else {
                    OperationResult.StandardFailure(StandardError.Unknown(null))
                }
            } catch (ex: Exception) {
                OperationResult.StandardFailure(ex.commonError())
            }
        }
    }

    override suspend fun specifyPinCode(pinCode: Code): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        return dispatchers.withIO {
            try {
                val request = SpecifyPinCodeRequest(pinCode.data)
                val response = registrationApiClient.specifyPinCode(request, userData.accessToken)
                val result = response.responseData
                if (result != null) {
                    OperationResult.Success(
                        SpecifyUserInfoResult(
                            userStatus = result.userStatus.toDomain(),
                            pinCodeLength = result.pinCodeLength?.let { Length(it) }
                        )
                    )
                } else {
                    OperationResult.StandardFailure(StandardError.Unknown(null))
                }
            } catch (ex: Exception) {
                OperationResult.StandardFailure(ex.commonError())
            }
        }
    }

    override suspend fun specifyResidenceAddress(residenceAddress: ResidenceAddress): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        return dispatchers.withIO {
            try {
                val request = SpecifyResidenceAddressRequest(
                    countryId = residenceAddress.country.id.data,
                    city = residenceAddress.city,
                    street = residenceAddress.street,
                    buildingNumber = residenceAddress.buildingNumber,
                    apartmentNumber = residenceAddress.apartment
                )
                val response = registrationApiClient.specifyResidenceAddress(request, userData.accessToken)
                val result = response.responseData
                if (result != null) {
                    OperationResult.Success(
                        SpecifyUserInfoResult(
                            userStatus = result.userStatus.toDomain(),
                            pinCodeLength = result.pinCodeLength?.let { Length(it) }
                        )
                    )
                } else {
                    OperationResult.StandardFailure(StandardError.Unknown(null))
                }
            } catch (ex: Exception) {
                OperationResult.StandardFailure(ex.commonError())
            }
        }
    }
}