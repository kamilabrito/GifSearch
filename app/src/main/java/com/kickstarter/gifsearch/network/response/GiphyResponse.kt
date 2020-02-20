package com.kickstarter.gifsearch.network.response

data class GiphyResponse(
  val data: List<GifItemResponse> = listOf<GifItemResponse>()
)