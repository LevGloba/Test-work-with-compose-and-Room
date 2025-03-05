// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    //ksp
    alias(libs.plugins.dev.tools.kps) apply false
    //Hilt Dagger 2
    alias(libs.plugins.dagger.hilt.android.plugin) apply false
}