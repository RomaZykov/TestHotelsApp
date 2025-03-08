pluginManagement {
    includeBuild("build-logic")
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

rootProject.name = "TestHotelsApp"

include(":app")
include(":features:hotelsFragment")
include(":features:hotelCompose")

include(":core:domain")
include(":core:data")
include(":core:network")
include(":core:exceptions")
include(":core:common")
include(":core:navigation")
include(":core:theme")
include(":core:ui")
