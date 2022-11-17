val composeVersion by extra { "1.2.1" }
val koinVersion by extra { "3.2.2" }
val koinComposeVersion by extra { "3.2.1" }
val coroutinesVersion by extra { "1.6.4" }
val coilVersion by extra { "2.2.2" }
val navigationVersion by extra { "2.5.2" }
val truthVersion by extra { "1.1.3" }
val lifecycleVersion by extra { "2.6.0-alpha02" }
val roomVersion by extra { "2.4.3" }
val datePickerVersion by extra { "0.8.1-rc" }

plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false

}