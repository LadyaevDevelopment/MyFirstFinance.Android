package ladyaev.development.myFirstFinance.data.mock

import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.OperationResult
import ladyaev.development.myFirstFinance.domain.entities.Country
import ladyaev.development.myFirstFinance.domain.entities.PolicyDocument
import ladyaev.development.myFirstFinance.domain.repository.misc.CountriesData
import ladyaev.development.myFirstFinance.domain.repository.misc.MiscRepository
import ladyaev.development.myFirstFinance.domain.repository.misc.PolicyDocumentsData
import javax.inject.Inject

class MiscRepositoryMock @Inject constructor() : MiscRepository {

    override suspend fun policyDocuments(): OperationResult<PolicyDocumentsData, Unit> {
        delay(500)
        return OperationResult.Success(
            PolicyDocumentsData(
                items = listOf(
                    PolicyDocument("Privacy Policy", DOCUMENT_URL),
                    PolicyDocument("Esign Consent", DOCUMENT_URL),
                    PolicyDocument("Communication Policy", DOCUMENT_URL),
                )
            )
        )
    }

    override suspend fun countries(): OperationResult<CountriesData, Unit> {
        delay(500)
        return OperationResult.Success(
            CountriesData(
                listOf(
                    Country(
                        "United Arab Emirates",
                        "+971",
                        "https://i.pinimg.com/originals/f5/ea/7f/f5ea7fb6ae1a02b5c6fd01a52e90f525.png",
                        listOf(
                            "(###)###-####",
                            "17-###-###",
                        )
                    ),
                    Country(
                        "Andorra",
                        "+971",
                        "https://avatars.mds.yandex.net/i?id=8a0209f2c66031786bc673915b0086918a91177a5fe1d3d9-5488408-images-thumbs&n=13",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania2",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania3",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania4",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),Country(
                        "Albania5",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania6",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania7",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania8",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania9",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),

                )
            )
        )
    }

    companion object {
        private const val DOCUMENT_URL = "https://сия.store"
    }
}