package ladyaev.development.myFirstFinance.core.di

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun timer(duration: Int): Flow<Int> = flow {
    for (i in duration downTo 0) {
        emit(i)
        delay(1000)
    }
}