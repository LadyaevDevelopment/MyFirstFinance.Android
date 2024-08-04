package ladyaev.development.myFirstFinance.core.common

interface PostBlock<T> {
    fun post(block: T.() -> Unit)
}