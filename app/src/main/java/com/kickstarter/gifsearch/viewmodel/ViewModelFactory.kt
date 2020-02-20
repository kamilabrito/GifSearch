package com.kickstarter.gifsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kickstarter.gifsearch.repository.GifRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: GifRepository) :
    ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
      return MainActivityViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown class name")
  }
}