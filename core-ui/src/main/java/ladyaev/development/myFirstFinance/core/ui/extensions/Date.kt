package ladyaev.development.myFirstFinance.core.ui.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toFormat(pattern: String): String {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(this)
}

fun Date.toFullDayFullMonthFullYearFormat() = toFormat("dd MMMM yyyy")

fun Date.to2day2month4yearFormat() = toFormat("dd.MM.yyyy")

fun Date.toTimeFormat() = toFormat("hh:mm")