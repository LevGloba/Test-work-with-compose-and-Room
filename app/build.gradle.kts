plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //kapt
    alias(libs.plugins.kapt.plugin)
    //ksp
    alias(libs.plugins.dev.tools.kps)
    //Hilt Dagger 2
    alias(libs.plugins.dagger.hilt.android.plugin)
}

android {
    namespace = "com.example.ip_test_task"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ip_test_task"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        /*kapt {
            arguments {arg("room.schemaLocation", "$projectDir/schemas")}
        }*/

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        setProperty("archivesBaseName", "ip-test-task")
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
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
    implementation(libs.androidx.lifecycle.viewmodel.compose)


    //RoomDataBase
    implementation(libs.room.gradle.plugin)
    implementation(libs.room.runtime)
    ksp(libs.ksp.room.complire)

    //Hilt Dagger 2
    implementation(libs.dagger.hilt.android)
    kapt(libs.kapt.hilt.dagger)
}

//Разрешить генерировать код
kapt {
    correctErrorTypes = true
}
