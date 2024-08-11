package ladyaev.development.myfirstfinance.data.api.configuration

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager

fun configuredChuckerInterceptor(context: Context) = ChuckerInterceptor.Builder(context)
    .collector(
        ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.FOREVER
        )
    )
    .maxContentLength(Long.MAX_VALUE)
    .alwaysReadResponseBody(true)
    .createShortcut(true)
    .build()