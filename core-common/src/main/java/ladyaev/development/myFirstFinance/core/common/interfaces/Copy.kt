package ladyaev.development.myFirstFinance.core.common.interfaces

interface Copy<T> {
    fun copy(data: T): T
}