package ladyaev.development.myFirstFinance.core.common

import java.util.Calendar
import java.util.Date

class Age(birthDate: Date) {
    val years = calculateAge(birthDate)

    companion object {
        private fun calculateAge(birthDate: Date): Int {
            val today = Calendar.getInstance()

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