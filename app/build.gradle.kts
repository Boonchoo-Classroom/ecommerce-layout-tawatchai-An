plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "scisrc.mobiledev.ecommercelayout"
    compileSdk = 35

    defaultConfig {
        applicationId = "scisrc.mobiledev.ecommercelayout"
        minSdk = 24  // ลด minSdk เพื่อรองรับอุปกรณ์เก่าขึ้น
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
        sourceCompatibility = JavaVersion.VERSION_17  // อัปเดตเป็น Java 17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"  // อัปเดตให้รองรับ JVM 17
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // ✅ เพิ่ม ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // ✅ เพิ่ม CircleIndicator3
    implementation("me.relex:circleindicator:2.1.6")

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // ของเดิม
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
