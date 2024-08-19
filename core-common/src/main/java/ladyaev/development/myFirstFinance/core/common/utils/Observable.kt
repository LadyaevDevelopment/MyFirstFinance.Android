package ladyaev.development.myFirstFinance.core.common.utils

import ladyaev.development.myFirstFinance.core.common.interfaces.Post
import ladyaev.development.myFirstFinance.core.common.utils.delegates.uniqueObservable

class Observable<T>(initialValue: T) : Post<T> {
    var value: T by uniqueObservable(initialValue) {
        observers.forEach {
            it.notify(value)
        }
    }

    private val observers: MutableList<Observer<T>> = mutableListOf()

    fun observe(observer: Observer<T>) {
        observers.add(observer)
    }

    operator fun invoke(): T = value

    operator fun invoke(newValue: T) {
        value = newValue
    }

    override fun post(data: T) {
        value = data
    }
}

fun <T> observableOf(initialValue: T) = Observable(initialValue)

interface Observer<T> {
    fun notify(value: T)
}