package ladyaev.development.myFirstFinance.core.common.utils

import java.util.Calendar
import javax.inject.Inject

interface CurrentDate {
    val date: Calendar

    class Base @Inject constructor() : CurrentDate {
        override val date: Calendar get() = Calendar.getInstance()
    }
}