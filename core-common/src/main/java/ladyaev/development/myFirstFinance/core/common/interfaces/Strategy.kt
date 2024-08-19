package ladyaev.development.myFirstFinance.core.common.interfaces

interface Strategy<T> {
    val resolved: T

    abstract class Base<T> : Strategy<T> {
        private var lastKeyArgument: Any? = null
        private var lastResolved: T? = null

        override val resolved: T get() = actual()

        abstract fun actualKeyArgument(): Any
        abstract fun resolve(): T

        private fun actual(): T {
            val actualKey = actualKeyArgument()
            if (lastResolved == null || actualKey != lastKeyArgument) {
                lastKeyArgument = actualKey
                lastResolved = resolve()
            } else {
                println()
            }
            return lastResolved!!
        }
    }
}