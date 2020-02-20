package com.kickstarter.gifsearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kickstarter.gifsearch.model.GifItem
import com.kickstarter.gifsearch.network.ApiStatus
import com.kickstarter.gifsearch.network.response.GiphyResponse
import com.kickstarter.gifsearch.repository.GifRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(private val repository: GifRepository) : ViewModel() {

  private val disposable = CompositeDisposable()
  private val responseLiveData = MutableLiveData<ApiStatus>()

  fun gifLiveData(): MutableLiveData<ApiStatus> {
    return responseLiveData
  }

  fun getGifs(word: String) {
    disposable.add(
        repository.getGif(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
              responseLiveData.value = ApiStatus.loading()
            }
            .subscribe({ result ->
              responseLiveData.value = ApiStatus.success(transformGifItem(result))
            }
                , { error ->
              responseLiveData.value = ApiStatus.error(error)
            })
    )
  }

  private fun transformGifItem(result: GiphyResponse): List<GifItem> {
    val gifsList = mutableListOf<GifItem>()
    for (gif in result.data) {
      val item = GifItem()
      item.id = gif.id
      item.title = gif.title
      item.url = gif.images.original.url
      item.height = gif.images.original.height
      item.width = gif.images.original.width
      gifsList.add(item)
    }
    return gifsList
  }

  override fun onCleared() {
    disposable.clear()
  }

}