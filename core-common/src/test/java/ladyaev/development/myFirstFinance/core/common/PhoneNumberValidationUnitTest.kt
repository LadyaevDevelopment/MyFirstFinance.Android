package ladyaev.development.myFirstFinance.core.common

import org.junit.Test

class PhoneNumberValidationUnitTest {
    @Test
    fun test_singleMask() {
        val masks = listOf(
            "(###)###-####"
        )
        val validation = PhoneNumberValidation.Base()

        var testInput = "7"
        var testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == "(7")

        testInput = "77"
        testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == "(77")

        testInput = "777"
        testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == "(777)")

        testInput = "7777"
        testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == "(777)7")

        testInput = "77777"
        testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == "(777)77")

        testInput = "777777"
        testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == "(777)777-")

        testInput = "7777777"
        testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == "(777)777-7")

        testInput = "77777777"
        testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == "(777)777-77")

        testInput = "777777777"
        testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == "(777)777-777")

        testInput = "7777777777"
        testResult = validation.test(testInput, masks)
        assert(testResult.completed)
        assert(testResult.formattedPhoneNumber == "(777)777-7777")

        testInput = "77777777777"
        testResult = validation.test(testInput, masks)
        assert(!testResult.completed)
        assert(testResult.formattedPhoneNumber == null)
    }
}