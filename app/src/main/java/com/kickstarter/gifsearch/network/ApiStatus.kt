package com.kickstarter.gifsearch.network

import com.kickstarter.gifsearch.model.GifItem
import com.kickstarter.gifsearch.network.Status.ERROR
import com.kickstarter.gifsearch.network.Status.LOADING
import com.kickstarter.gifsearch.network.Status.SUCESS

class ApiStatus (val status: Status,
    val data: List<GifItem>?,
    val error: Throwable?) {

  companion object{
    fun loading() : ApiStatus {
      return ApiStatus(LOADING, null, null)
    }

    fun success(data: List<GifItem>) : ApiStatus {
      return ApiStatus(SUCESS, data, null)
    }

    fun error(error: Throwable) : ApiStatus {
      return ApiStatus(ERROR, null, error)
    }
  }
}