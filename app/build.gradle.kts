import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.symbol.processing)
}

val keyPropertiesFile = rootProject.file("keys.properties")
val keyProperties = Properties()
keyProperties.load(FileInputStream(keyPropertiesFile))


android {
    signingConfigs {
        create("release") {
            storeFile = file(keyProperties["storeFile"] as String)
            storePassword = keyProperties["storePassword"] as String
            keyAlias = keyProperties["keyAlias"] as String
            keyPassword = keyProperties["keyPassword"] as String
        }

        getByName("debug") {
            storeFile = file(keyProperties["storeFile"] as String)
            storePassword = keyProperties["storePasswordDebug"] as String
            keyAlias = keyProperties["keyAliasDebug"] as String
            keyPassword = keyProperties["keyPasswordDebug"] as String
        }
    }
    namespace = "com.decide.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.decide.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        addManifestPlaceholders(
            mapOf(
                "VKIDClientID" to keyProperties["VKIDClientID"] as String,
                "VKIDClientSecret" to keyProperties["VKIDClientSecret"] as String,
                "VKIDRedirectHost" to keyProperties["VKIDRedirectHost"] as String,
                "VKIDRedirectScheme" to keyProperties["VKIDRedirectScheme"] as String,
            )
        )
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":uikit"))

    //VK
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.onetap.compose)

    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.kotlinx.immutable)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.room.compiler)


    //Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //Coil
    implementation(libs.coil.compose)

    //Datastore
    implementation(libs.datastore)

    //Timber
    implementation(libs.timber)

    //ViewModel+Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    //Firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.config)

    //Network
    implementation(libs.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    //SplashScreen
    implementation(libs.splashscreen)

    //Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Yandex ads
    implementation(libs.yandex.mobileads)

    //Leak Canary
    debugImplementation(libs.leak.canary)
}