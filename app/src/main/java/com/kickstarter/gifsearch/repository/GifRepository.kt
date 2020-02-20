package com.kickstarter.gifsearch.repository

import com.kickstarter.gifsearch.network.RetrofitInterface
import com.kickstarter.gifsearch.network.response.GiphyResponse
import io.reactivex.Observable

class GifRepository (private val retrofitInterface: RetrofitInterface){
  fun getGif(word: String): Observable<GiphyResponse> {
    return retrofitInterface.getGifs(word)
  }
}