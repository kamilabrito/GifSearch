package com.kickstarter.gifsearch.network

import com.kickstarter.gifsearch.network.response.GiphyResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

  @GET("/v1/gifs/search?api_key=229ac3e932794695b695e71a9076f4e5&limit=25&offset=0&rating=G&lang=en&")
  fun getGifs(@Query ("q") word:String) : Observable<GiphyResponse>

}