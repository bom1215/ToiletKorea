buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false

}

