package ladyaev.development.myfirstfinance.domain.repositories.misc.requirePolicyDocuments

import ladyaev.development.myfirstfinance.domain.entities.PolicyDocument

data class RequirePolicyDocumentsResult(
    val items: List<PolicyDocument>
)