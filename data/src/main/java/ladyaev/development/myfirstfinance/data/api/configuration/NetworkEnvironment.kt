package ladyaev.development.myfirstfinance.data.api.configuration

interface NetworkEnvironment {
    val baseApiUrl: String

    class Base : NetworkEnvironment {
        override val baseApiUrl = DEBUG_URL

        companion object {
            private const val DEBUG_URL = "http://192.168.0.8/myffin/"
        }
    }
}