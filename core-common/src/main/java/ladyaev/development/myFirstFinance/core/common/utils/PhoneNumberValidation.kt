package ladyaev.development.myFirstFinance.core.common.utils

import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import javax.inject.Inject
import kotlin.math.min

interface PhoneNumberValidation {
    fun test(phoneNumber: String, phoneNumberMasks: List<PhoneNumber>): TestResult
    fun normalizedNumber(phoneNumber: String): String

    data class TestResult(
        val formattedPhoneNumber: PhoneNumber?,
        val completed: Boolean
    )

    class Base @Inject constructor() : PhoneNumberValidation {
        override fun test(phoneNumber: String, phoneNumberMasks: List<PhoneNumber>): TestResult {
            if (phoneNumber.isBlank()) {
                return TestResult(null, false)
            }
            val cleanNumber = normalizedNumber(phoneNumber)

            val matchingResults = mutableListOf<InternalResult>()
            for (mask in phoneNumberMasks) {
                val cleanMask = normalizedMask(mask)
                if (matchesMask(cleanNumber, cleanMask)) {
                    val formattedPhoneNumber = applyMask(phoneNumber, mask)
                    matchingResults.add(
                        InternalResult(
                            formattedPhoneNumber = formattedPhoneNumber,
                            cleanPhoneNumber = phoneNumber,
                            mask = mask,
                            cleanMask = cleanMask,
                            completed = formattedPhoneNumber.length == mask.length
                        )
                    )
                }
            }
            matchingResults.firstOrNull { it.completed }?.let {
                return TestResult(it.formattedPhoneNumber, it.completed)
            }
            matchingResults.sortByDescending {
                maxExactMatchingPrefixLength(it.cleanPhoneNumber, it.cleanMask)
            }

            return matchingResults.firstOrNull()?.let {
                TestResult(it.formattedPhoneNumber, it.completed)
            } ?: TestResult(null, false)
        }

        override fun normalizedNumber(phoneNumber: String): String {
            return phoneNumber.replace(Regex("[^0-9]"), "")
        }

        private fun normalizedMask(mask: PhoneNumber): String {
            return mask.toString().replace(Regex("[^0-9#]"), "")
        }

        private fun normalizedMask(mask: String): String {
            return mask.replace(Regex("[^0-9#]"), "")
        }

        private fun matchesMask(phoneNumber: String, mask: String): Boolean {
            return phoneNumber.length <= mask.length && mask.zip(phoneNumber).all { (m, n) ->
                m == '#' || m == n
            }
        }

        private fun maxExactMatchingPrefixLength(s1: String, s2: String): Int {
            var prefixLength = 0
            for (index in 0..<min(s1.length, s2.length)) {
                if (s1[index] == s2[index]) {
                    prefixLength++
                }
            }
            return prefixLength
        }

        private fun applyMask(number: String, mask: PhoneNumber): PhoneNumber {
            val cleanNumber = normalizedNumber(number)
            if (cleanNumber.isEmpty() || cleanNumber.length == normalizedMask(mask.countryCode).length) {
                return PhoneNumber(mask.countryCode)
            }
            val builder = StringBuilder()
            var index = 0

            for (char in mask.toString()) {
                if (!char.isDigit() && char != '#') {
                    builder.append(char)
                } else if (index < cleanNumber.length) {
                    builder.append(cleanNumber[index++])
                } else {
                    break
                }
            }
            val formattedPhoneNumber = builder.toString()
            return PhoneNumber(
                mask.countryCode,
                formattedPhoneNumber.substring(mask.countryCode.length).trim()
            )
        }

        private data class InternalResult(
            val formattedPhoneNumber: PhoneNumber,
            val cleanPhoneNumber: String,
            val mask: PhoneNumber,
            val cleanMask: String,
            val completed: Boolean
        )
    }
}