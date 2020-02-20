package com.kickstarter.gifsearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kickstarter.gifsearch.R
import com.kickstarter.gifsearch.model.GifItem

class GifRecyclerViewAdapter : RecyclerView.Adapter<GifRecyclerViewAdapter.GifItemViewHolder>() {

  private var gifList = listOf<GifItem>()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): GifItemViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.gif_item_row_main_activity, parent, false)
    return GifItemViewHolder(view)
  }

  override fun getItemCount(): Int {
    return gifList.size
  }

  override fun onBindViewHolder(
    holder: GifItemViewHolder,
    position: Int
  ) {
    val item = gifList.get(position)
    holder.bind(item)
  }

  fun updateGifList(gifList: List<GifItem>) {
    this.gifList = gifList
    notifyDataSetChanged()
  }

  class GifItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val gifImageView: AppCompatImageView = v.findViewById(R.id.gifImageView)

    fun bind(gifItem: GifItem) {
      val params = gifImageView.layoutParams as LinearLayout.LayoutParams
      params.height = gifItem.height.toInt()
      params.width = gifItem.width.toInt()
      gifImageView.layoutParams = params

      Glide.with(gifImageView)
          .load(gifItem.url)
          .error(android.R.drawable.ic_dialog_alert)
          .placeholder(android.R.drawable.arrow_down_float)
          .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
          .into(gifImageView)
    }
  }
}





