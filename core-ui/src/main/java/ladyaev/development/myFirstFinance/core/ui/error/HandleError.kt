package ladyaev.development.myFirstFinance.core.ui.error

import ladyaev.development.myFirstFinance.core.common.utils.ManageResources
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myFirstFinance.core.common.interfaces.Transformation
import ladyaev.development.myFirstFinance.core.resources.R

interface HandleError : Transformation<StandardError, String> {

    open class Base(private val manageResources: ManageResources) : HandleError {
        override fun map(data: StandardError): String {
            return when (data) {
                StandardError.Connection -> manageResources.string(R.string.commonError_connection)
                is StandardError.External -> manageResources.string(R.string.commonError_external)
                is StandardError.Unknown -> manageResources.string(R.string.commonError_unknown)
                StandardError.NeedToUpdateApplication -> manageResources.string(R.string.commonError_needToUpdateApplication)
            }
        }
    }

    class Debug : HandleError {
        override fun map(data: StandardError): String {
            return when (data) {
                StandardError.Connection -> "connection error"
                is StandardError.External -> "external error\n\n${data.errorMessage}"
                is StandardError.Unknown -> "unknown error\n\n${data.errorMessage}"
                StandardError.NeedToUpdateApplication -> "need to update app"
            }
        }
    }
}