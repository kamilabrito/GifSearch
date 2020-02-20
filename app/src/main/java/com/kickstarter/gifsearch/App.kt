package com.kickstarter.gifsearch

import android.app.Application
import android.content.Context
import com.kickstarter.gifsearch.di.AppComponent
import com.kickstarter.gifsearch.di.AppModule
import com.kickstarter.gifsearch.di.DaggerAppComponent
import com.kickstarter.gifsearch.di.NetworkModule

class App : Application() {
  var appComponent: AppComponent? = null
  var context: Context? = null
  override fun onCreate() {
    super.onCreate()
    context = this
    appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .networkModule(NetworkModule())
        .build()
  }

  override fun attachBaseContext(context: Context?) {
    super.attachBaseContext(context)
  }
}