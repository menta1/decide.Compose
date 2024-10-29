package com.decide.app

import android.app.Application
import com.vk.id.VKID
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        VKID.init(this)
        Timber.plant(Timber.DebugTree())
    }
}