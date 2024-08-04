package ladyaev.development.myFirstFinance.core.common

interface Post<T> {
    fun post(data: T)
}