package ladyaev.development.myFirstFinance.core.common.utils.delegates

import ladyaev.development.myFirstFinance.core.common.utils.DispatchUpdates

fun <T, M> dispatchUpdates(initialValue: T, dispatchUpdates: DispatchUpdates<M>): UniqueObservable<T> {
    return uniqueObservable(initialValue, dispatchUpdates::map)
}