package ladyaev.development.myFirstFinance.core.common.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

interface UserData {
    var accessToken: String
    var refreshToken: String

    class Base @Inject constructor(context: Context) : UserData {

        private val preferences: SharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

        override var accessToken: String
            get() = preferences.getString("accessToken", "")!!
            set(value) = preferences.edit(true) { putString("accessToken", value) }

        override var refreshToken: String
            get() = preferences.getString("refreshToken", "")!!
            set(value) = preferences.edit(true) { putString("refreshToken", value) }
    }
}