plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.quizodyssey"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.quizodyssey"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    implementation ("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.android.volley:volley:1.2.1")
    implementation ("com.google.code.gson:gson:2.13.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.airbnb.android:lottie:6.6.7")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}