package com.github.aimanzaki.dotaheroes

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()

  }
}
