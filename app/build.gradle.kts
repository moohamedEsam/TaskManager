plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
    kotlin("plugin.serialization") version "1.7.10"
}

android {
    namespace = "com.example.taskmanager"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.taskmanager"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures.compose = true
    kotlinOptions.jvmTarget = "1.8"
    composeOptions.kotlinCompilerExtensionVersion = "1.3.1"
    packagingOptions {
        resources.excludes += "META-INF/atomicfu.kotlin_module"
    }
}

dependencies {
    val composeVersion: String by rootProject.extra
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material3:material3:1.0.0-beta03")
    testImplementation("junit:junit:4.13.2")
    testImplementation("junit:junit:4.12")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    //icons
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    // constraint layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //navigation
    val navigationVersion: String by rootProject.extra
    implementation("androidx.navigation:navigation-compose:$navigationVersion")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.0")

    val koinVersion: String by rootProject.extra
    val koinComposeVersion: String by rootProject.extra
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinComposeVersion")

    // Coroutines
    val coroutinesVersion: String by rootProject.extra
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutinesVersion")

    // ViewModel
    val lifecycleVersion: String by rootProject.extra
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")


    // coil
    val coilVersion: String by rootProject.extra
    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation("io.coil-kt:coil-gif:$coilVersion")

    // room
    val roomVersion: String by rootProject.extra
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")

    //shape shift
    implementation("dev.krud:shapeshift:0.6.0")

    //test
    val truthVersion: String by rootProject.extra
    testImplementation("com.google.truth:truth:$truthVersion")
    testImplementation("app.cash.turbine:turbine:0.12.0")
    testImplementation("org.mockito:mockito-core:2.19.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    androidTestImplementation("com.google.truth:truth:$truthVersion")
    androidTestImplementation("app.cash.turbine:turbine:0.12.0")
    androidTestImplementation("org.mockito:mockito-core:2.19.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}