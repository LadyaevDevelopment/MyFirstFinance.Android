package ladyaev.development.myFirstFinance.core.common.utils

import java.util.Calendar
import java.util.Date

class Age(
    birthDate: Date,
    currentDate: CurrentDate
) {
    val years = calculateAge(birthDate, currentDate)

    companion object {
        private fun calculateAge(birthDate: Date, currentDate: CurrentDate): Int {
            val today = currentDate.date

            val calendar = Calendar.getInstance().apply {
                time = birthDate
            }
            val birthYear = calendar.get(Calendar.YEAR)
            val birthMonth = calendar.get(Calendar.MONTH)
            val birthDay = calendar.get(Calendar.DAY_OF_MONTH)

            val currentYear = today.get(Calendar.YEAR)
            val currentMonth = today.get(Calendar.MONTH)
            val currentDay = today.get(Calendar.DAY_OF_MONTH)

            var age = currentYear - birthYear

            if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
                age--
            }

            return age
        }
    }
}