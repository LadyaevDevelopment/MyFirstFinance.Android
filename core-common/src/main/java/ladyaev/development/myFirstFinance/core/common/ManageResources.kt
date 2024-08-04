package ladyaev.development.myFirstFinance.core.common

import android.content.Context
import androidx.annotation.StringRes

interface ManageResources {
    fun string(@StringRes id: Int, vararg formatArgs: Any): String

    class Base(private val context: Context) : ManageResources {
        override fun string(id: Int, vararg formatArgs: Any) = context.getString(id, *formatArgs)
    }
}