pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

includeBuild("build-logic")

rootProject.name = "Simple words"
include(":app")
include(":domain")
include(":data")
include(":feature:quizlist")
include(":core:common")
include(":core:ui")
include(":feature:quizdetails")
include(":feature:quizcreate")
