object AppConfig {
    const val minSdkVersion = 26
    const val targetSdkVersion = 31
    const val compileSdkVersion = 31
}

object Versions {
    const val supportLib = "1.0.2"
    const val legacySupport = "1.0.0"
    const val media = "1.0.1"
    const val constraintLayout = "1.1.3"
    const val recyclerView = "1.0.0"
    const val cardView = "1.0.0"
    const val browser = "1.0.0"
    const val glide = "4.9.0"
    const val rxAndroid = "2.0.2"
    const val daggerCompiler = "2.23.2"
    const val firebaseCore = "16.0.8"
    const val crashlytics = "17.2.2"
    const val crashlyticsPlugin = "2.3.0"
    const val paging = "2.1.0"
    const val liveData = "2.2.0-alpha01"
    const val threeTenAbp = "1.1.0"
    const val kotlinGradlePlugin = "1.3.31"
    const val kotlinStdlibJdk7 = "1.3.31"
    const val gson = "2.8.2"
    const val retrofit = "2.3.0"
    const val gsonConverter = "2.3.0"
    const val rxAdapter = "2.3.0"
    const val room = "2.1.0"
    const val roomCompiler = "2.1.0"
    const val roomRxJava = "1.1.1"
    const val rxJava = "2.1.10"
    const val dagger = "2.23.2"
    const val viewModel = "2.2.0-alpha01"
    const val lifeCycleExtensions = "2.2.0-alpha01"
    const val mockitoCore = "2.28.2"
    const val mockitoKotlin = "1.6.0"
    const val jUnit = "4.12"
    const val hilt = "2.28-alpha"
    const val hiltArchComponents = "1.0.0-alpha01"
}

object Libs {
    // Android
    const val supportAppCompatV7 = "androidx.appcompat:appcompat:${Versions.supportLib}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    const val media = "androidx.media:media:${Versions.media}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val browser = "androidx.browser:browser:${Versions.browser}"
    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata:${Versions.liveData}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.viewModel}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleExtensions}"
    const val crashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:2.3.0"

    // 3rd party
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerCompiler}"
    const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
    const val crashlytics = "com.google.firebase:firebase-crashlytics:${Versions.crashlytics}"
    const val threeTenAbp = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenAbp}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    const val rxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.rxAdapter}"
    const val roomRxJava = "android.arch.persistence.room:rxjava2:${Versions.roomRxJava}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinStdlibJdk7}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"
    const val jUnit = "junit:junit:${Versions.jUnit}"

    // Hilt
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    // Hilt Android Architecture Components
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltArchComponents}"
    const val hiltArchComponentsCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltArchComponents}"
}