package ladyaev.development.myFirstFinance.core.common

interface Copy<T> {
    fun copy(data: T): T
}