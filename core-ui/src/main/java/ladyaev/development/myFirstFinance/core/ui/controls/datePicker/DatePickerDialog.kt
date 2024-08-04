package ladyaev.development.myFirstFinance.core.ui.controls.datePicker

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.util.Calendar
import java.util.Date

fun datePickerDialog(
    context: Context,
    initialDate: Date? = null,
    dateChanged: (date: Date) -> Unit
): DatePickerDialog {
    val calendar = Calendar.getInstance().apply {
        time = initialDate ?: Date()
    }
    return DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            Calendar.getInstance().apply {
                this[Calendar.YEAR] = year
                this[Calendar.MONTH] = month
                this[Calendar.DAY_OF_MONTH] = day
                this[Calendar.HOUR_OF_DAY] = 0
                this[Calendar.MINUTE] = 0
                this[Calendar.SECOND] = 0
                this[Calendar.MILLISECOND] = 0
                dateChanged(time)
            }
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}