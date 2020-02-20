package com.kickstarter.gifsearch.network.response

data class GifItemResponse(
  val id: String,
  val title: String,
  val images: GifImages
)