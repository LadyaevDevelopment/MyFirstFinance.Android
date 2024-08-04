pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyFirstFinance.Android"
include(":app")
include(":core-ui")
include(":domain-entities")
include(":domain-repository")
include(":domain-business")
include(":data-mock")
include(":data-api")
include(":core-common")
include(":feature-setupUser")
include(":core-di")
include(":feature-dashboard")
