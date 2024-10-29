import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlin.symbol.processing) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.google.firebase.appdistribution) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("vkid.manifest.placeholders") version "1.0.0" apply true
}
vkidManifestPlaceholders {
    val keyPropertiesFile = rootProject.file("keys.properties")
    val keyProperties = Properties()
    keyProperties.load(FileInputStream(keyPropertiesFile))
    val clientId = keyProperties["VKIDClientID"]
    val clientSecret = keyProperties["VKIDClientSecret"]

    init(
        clientId = clientId.toString(),
        clientSecret = clientSecret.toString(),
    )
}