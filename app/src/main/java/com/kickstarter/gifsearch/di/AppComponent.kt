package com.kickstarter.gifsearch.di

import com.kickstarter.gifsearch.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class])
@Singleton
interface AppComponent {
  fun doInjection(mainActivity: MainActivity)
}