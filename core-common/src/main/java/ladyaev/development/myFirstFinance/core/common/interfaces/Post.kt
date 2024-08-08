package ladyaev.development.myFirstFinance.core.common.interfaces

interface Post<T> {
    fun post(data: T)
}