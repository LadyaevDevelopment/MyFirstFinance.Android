package ladyaev.development.myFirstFinance.core.common.interfaces

interface Transformation<Source : Any, Target : Any> {

    fun map(data: Source): Target

    interface Unit<T : Any> : Transformation<T, kotlin.Unit>
}