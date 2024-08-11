package ladyaev.development.myFirstFinance.core.common.extensions

fun<T : Any> T.applyIf(condition: Boolean, block: T.() -> T): T {
    return if (condition) {
        block(this)
    } else this
}