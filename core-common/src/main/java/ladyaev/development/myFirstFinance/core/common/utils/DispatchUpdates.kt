package ladyaev.development.myFirstFinance.core.common.utils

abstract class DispatchUpdates<T>(initialValue: T) {
    var actual: T = initialValue
        get private set

    fun dispatch() {
        actual = map()
    }

    abstract fun map(): T
}