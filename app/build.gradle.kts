plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
}

android {
    namespace = "com.univalle.dogapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.univalle.dogapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        jvmTarget = "11"
        freeCompilerArgs += listOf("-Xlint:deprecation")
    }
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/src/main/schemas")
        arg("room.incremental", "true")
        arg("room.expandProjection", "true")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    //Navigation Component (para usar Fragmentos y navegación)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity.ktx)

    //Lifecycle ViewModel (para MVVM)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    //biometria
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.biometric.v120alpha05)
    
    //animation
    implementation(libs.lottie)
    
    //materials
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material.v1120)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    
    //database Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.runtime.v252)
    ksp(libs.androidx.room.compiler.v252)
    implementation(libs.glide)
    kapt(libs.compiler)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)



}