package ladyaev.development.myFirstFinance.core.ui.transmission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ladyaev.development.myFirstFinance.core.common.interfaces.Post
import ladyaev.development.myFirstFinance.core.common.interfaces.Read
import java.lang.Exception

interface Transmission {

    interface Source<T : Any> : Read<T>

    interface Update<T : Any> : Post<T>

    interface Mutable<DataTransport: Any, Data : Any> : Source<DataTransport>, Update<Data>

    open class DecoratorBase<T : Any>() : Mutable<T, T> {
        private var internalData : T? = null

        override fun read() = internalData ?: throw Exception("Wrapped data must be initialized")

        override fun post(data: T) {
            internalData = data
        }

        constructor(initialData: T) : this() {
            internalData = initialData
        }
    }

    abstract class LiveDataAbstract<T : Any>(
        protected val mutableLiveData: MutableLiveData<T>
    ) : Mutable<LiveData<T>, T> {

        override fun read(): LiveData<T> = mutableLiveData
    }

    open class LiveDataBase<T : Any> : LiveDataAbstract<T>(MutableLiveData()) {
        override fun post(data: T) {
            mutableLiveData.value = data
        }
    }

    open class SingleLiveEventBase<T : Any> : LiveDataAbstract<T>(SingleLiveEvent()) {
        override fun post(data: T) {
            mutableLiveData.value = data
        }
    }
}