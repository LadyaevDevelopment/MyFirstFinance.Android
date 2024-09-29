package ladyaev.development.myFirstFinance.core.common.utils

fun <T1 : Any, T2 : Any, TR : Any> memoizedComputable(
    mapper: (T1, T2) -> TR
): (T1, T2) -> TR {
    var lastArg1: T1? = null
    var lastArg2: T2? = null
    var cachedResult: TR? = null

    return { arg1: T1, arg2: T2 ->
        if (cachedResult != null && arg1 == lastArg1 && arg2 == lastArg2) {
            cachedResult!!
        } else {
            val result = mapper(arg1, arg2)
            lastArg1 = arg1
            lastArg2 = arg2
            cachedResult = result
            result
        }
    }
}

fun <T1 : Any, T2 : Any, T3 : Any, TR : Any> memoizedComputable(
    mapper: (T1, T2, T3) -> TR
): (T1, T2, T3) -> TR {
    var lastArg1: T1? = null
    var lastArg2: T2? = null
    var lastArg3: T3? = null
    var cachedResult: TR? = null

    return { arg1: T1, arg2: T2, arg3: T3 ->
        if (cachedResult != null && arg1 == lastArg1 && arg2 == lastArg2 && arg3 == lastArg3) {
            cachedResult!!
        } else {
            val result = mapper(arg1, arg2, arg3)
            lastArg1 = arg1
            lastArg2 = arg2
            lastArg3 = arg3
            cachedResult = result
            result
        }
    }
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, TR : Any> memoizedComputable(
    mapper: (T1, T2, T3, T4) -> TR
): (T1, T2, T3, T4) -> TR {
    var lastArg1: T1? = null
    var lastArg2: T2? = null
    var lastArg3: T3? = null
    var lastArg4: T4? = null
    var cachedResult: TR? = null

    return { arg1: T1, arg2: T2, arg3: T3, arg4: T4 ->
        if (cachedResult != null && arg1 == lastArg1 && arg2 == lastArg2 && arg3 == lastArg3 && arg4 == lastArg4) {
            cachedResult!!
        } else {
            val result = mapper(arg1, arg2, arg3, arg4)
            lastArg1 = arg1
            lastArg2 = arg2
            lastArg3 = arg3
            lastArg4 = arg4
            cachedResult = result
            result
        }
    }
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, TR : Any> memoizedComputable(
    mapper: (T1, T2, T3, T4, T5) -> TR
): (T1, T2, T3, T4, T5) -> TR {
    var lastArg1: T1? = null
    var lastArg2: T2? = null
    var lastArg3: T3? = null
    var lastArg4: T4? = null
    var lastArg5: T5? = null
    var cachedResult: TR? = null

    return { arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5 ->
        if (cachedResult != null && arg1 == lastArg1 && arg2 == lastArg2 && arg3 == lastArg3 && arg4 == lastArg4 && arg5 == lastArg5) {
            cachedResult!!
        } else {
            val result = mapper(arg1, arg2, arg3, arg4, arg5)
            lastArg1 = arg1
            lastArg2 = arg2
            lastArg3 = arg3
            lastArg4 = arg4
            lastArg5 = arg5
            cachedResult = result
            result
        }
    }
}