package ladyaev.development.myFirstFinance.core.common.utils.delegates

import kotlin.reflect.KProperty

class UniqueObservable<T>(initialValue: T, private val onChange: (oldValue: T, newValue: T) -> Unit) {
    private var field: T = initialValue

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = field

    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T) {
        val oldValue = field
        if (oldValue != newValue) {
            field = newValue
            onChange(oldValue, newValue)
        }
    }
}

fun <T> uniqueObservable(initialValue: T, onChange: () -> Unit): UniqueObservable<T> {
    return UniqueObservable(initialValue) { _, _ ->
        onChange()
    }
}