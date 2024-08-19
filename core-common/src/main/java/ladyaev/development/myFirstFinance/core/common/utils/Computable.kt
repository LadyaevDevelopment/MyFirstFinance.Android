package ladyaev.development.myFirstFinance.core.common.utils

abstract class Computable2<T, T1, T2>(initialValue: T, val t1: Observable<T1>, val t2: Observable<T2>) {
    var actual: T = initialValue
        get private set

    init {
        t1.observe(object : Observer<T1> {
            override fun notify(value: T1) {
                actual = map(t1.value, t2.value)
            }
        })
        t2.observe(object : Observer<T2> {
            override fun notify(value: T2) {
                actual = map(t1.value, t2.value)
            }
        })
    }

    abstract fun map(data1: T1, data2: T2): T
}

abstract class Computable3<T, T1, T2, T3>(initialValue: T, val t1: Observable<T1>, val t2: Observable<T2>, val t3: Observable<T3>) {
    var actual: T = initialValue
        get private set

    init {
        t1.observe(object : Observer<T1> {
            override fun notify(value: T1) {
                actual = map(t1.value, t2.value, t3.value)
            }
        })
        t2.observe(object : Observer<T2> {
            override fun notify(value: T2) {
                actual = map(t1.value, t2.value, t3.value)
            }
        })
        t3.observe(object : Observer<T3> {
            override fun notify(value: T3) {
                actual = map(t1.value, t2.value, t3.value)
            }
        })
    }

    abstract fun map(data1: T1, data2: T2, data3: T3): T
}


fun <T, T1, T2> computable(
    initialValue: T,
    t1: Observable<T1>,
    t2: Observable<T2>,
    map: (t1: T1, t2: T2) -> T
) : Computable2<T, T1, T2> {
    return object : Computable2<T, T1, T2>(initialValue, t1, t2) {
        override fun map(data1: T1, data2: T2) = map(data1, data2)
    }
}

fun <T, T1, T2, T3> computable(
    initialValue: T,
    t1: Observable<T1>,
    t2: Observable<T2>,
    t3: Observable<T3>,
    map: (t1: T1, t2: T2, t3: T3) -> T
) : Computable3<T, T1, T2, T3> {
    return object : Computable3<T, T1, T2, T3>(initialValue, t1, t2, t3) {
        override fun map(data1: T1, data2: T2, data3: T3) = map(data1, data2, data3)
    }
}