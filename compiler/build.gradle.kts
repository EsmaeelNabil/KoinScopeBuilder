plugins {
    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
    alias(libs.plugins.multiplatform)
}

kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                implementation("com.google.devtools.ksp:symbol-processing-api:2.0.20-1.0.24")
                implementation(project(":api"))
            }
        }
    }

}
