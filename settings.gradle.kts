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
include(":core:common")
include(":core:ui")
include(":feature:quizlist")
include(":feature:quizdetails")
include(":feature:quizcreate")
include(":feature:exercise")
include(":feature:signin")
include(":feature:account")
include(":feature:signup")
include(":feature:welcome")
include(":feature:changepassword")
include(":feature:deleteaccount")
