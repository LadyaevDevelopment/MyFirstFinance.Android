package ladyaev.development.myfirstfinance.data.mock

import kotlinx.coroutines.delay
import ladyaev.development.myfirstfinance.domain.entities.Country
import ladyaev.development.myfirstfinance.domain.entities.PolicyDocument
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.requireCountries.RequireCountriesResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository
import ladyaev.development.myfirstfinance.domain.repositories.misc.requirePolicyDocuments.RequirePolicyDocumentsResult
import javax.inject.Inject

class MiscRepositoryMock @Inject constructor() : MiscRepository {

    override suspend fun policyDocuments(): OperationResult<RequirePolicyDocumentsResult, Unit> {
        delay(500)
        return OperationResult.Success(
            RequirePolicyDocumentsResult(
                items = listOf(
                    PolicyDocument(
                        "Privacy Policy",
                        DOCUMENT_URL
                    ),
                    PolicyDocument(
                        "Esign Consent",
                        DOCUMENT_URL
                    ),
                    PolicyDocument(
                        "Communication Policy",
                        DOCUMENT_URL
                    ),
                )
            )
        )
    }

    override suspend fun countries(): OperationResult<RequireCountriesResult, Unit> {
        delay(500)
        return OperationResult.Success(
            RequireCountriesResult(
                listOf(
                    Country(
                        "United Arab Emirates",
                        "AE",
                        "+971",
                        "https://i.pinimg.com/originals/f5/ea/7f/f5ea7fb6ae1a02b5c6fd01a52e90f525.png",
                        listOf(
                            "(###)###-####",
                            "17-###-###",
                        )
                    ),
                    Country(
                        "Andorra",
                        "AD",
                        "+971",
                        "https://avatars.mds.yandex.net/i?id=8a0209f2c66031786bc673915b0086918a91177a5fe1d3d9-5488408-images-thumbs&n=13",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania",
                        "AB",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania2",
                        "AB",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania3",
                        "AB",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania4",
                        "AB",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania5",
                        "AB",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania6",
                        "AB",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania7",
                        "AB",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania8",
                        "AB",
                        "+971",
                        "https://fikiwiki.com/uploads/posts/2022-02/1644975879_8-fikiwiki-com-p-kartinki-flag-albanii-8.jpg",
                        listOf(
                            "(###)###-####"
                        )
                    ),
                    Country(
                        "Albania9",
                        "AB",
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