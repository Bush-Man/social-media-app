plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.app.socialmedia"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.app.socialmedia"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Retrofit
    implementation(libs.converter.gson)
    implementation(libs.retrofit)

    //Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation (libs.androidx.hilt.navigation.compose)

    //Raamcoasta navigation
    implementation(libs.core.vversion)
    ksp(libs.ksp.vversion)
    
    //Okhttp
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    //Pager
    implementation (libs.accompanist.pager)
    implementation (libs.accompanist.pager.indicators)

    //Data store
    implementation(libs.androidx.datastore.preferences)

    //socket io
    // https://mvnrepository.com/artifact/io.socket/socket.io-client
    implementation(libs.socket.io.client)

    //scalar convertor replace of gson convertor
    implementation(libs.converter.scalars)
}