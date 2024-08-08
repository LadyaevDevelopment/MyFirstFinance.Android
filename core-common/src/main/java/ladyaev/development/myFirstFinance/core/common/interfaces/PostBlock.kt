package ladyaev.development.myFirstFinance.core.common.interfaces

interface PostBlock<T> {
    fun post(block: T.() -> Unit)
}