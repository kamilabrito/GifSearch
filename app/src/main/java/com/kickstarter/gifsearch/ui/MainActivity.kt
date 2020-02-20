package com.kickstarter.gifsearch.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kickstarter.gifsearch.App
import com.kickstarter.gifsearch.R
import com.kickstarter.gifsearch.adapters.GifRecyclerViewAdapter
import com.kickstarter.gifsearch.model.GifItem
import com.kickstarter.gifsearch.network.ApiStatus
import com.kickstarter.gifsearch.network.NetworkUtils
import com.kickstarter.gifsearch.network.Status.ERROR
import com.kickstarter.gifsearch.network.Status.LOADING
import com.kickstarter.gifsearch.network.Status.SUCESS
import com.kickstarter.gifsearch.viewmodel.MainActivityViewModel
import com.kickstarter.gifsearch.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.searchEditText
import kotlinx.android.synthetic.main.activity_main.searchImageButton
import kotlinx.android.synthetic.main.activity_main.searchProgressBar
import kotlinx.android.synthetic.main.activity_main.searchRecyclerView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory
  var viewModel: MainActivityViewModel? = null
  val gifRecyclerViewAdapter = GifRecyclerViewAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    (application as App).appComponent?.let { it ->
      it.doInjection(this)
    }
    viewModel = ViewModelProviders.of(this, viewModelFactory)
        .get(MainActivityViewModel::class.java)
    viewModel?.let { it ->
      it.gifLiveData()
          .observe(this, Observer { response ->
            handleResponse(response)
          })
    }

    searchImageButton.setOnClickListener {
      if (!searchEditText.text.isNullOrEmpty()) {
        viewModel?.let { it ->
          if (NetworkUtils.hasNetWorkConnection(this)) {
            it.getGifs(searchEditText.text.toString())
          } else {
            showAlertDialog(getString(R.string.error), getString(R.string.no_internet_connection))
          }
        }
      } else {
        showAlertDialog(getString(R.string.error), getString(R.string.invalid_text))
      }
    }
  }

  private fun handleResponse(apiStatus: ApiStatus) {
    when (apiStatus.status) {
      LOADING -> searchProgressBar.visibility = View.VISIBLE
      SUCESS -> {
        renderSuccessResponse(apiStatus.data)
        searchProgressBar.visibility = View.GONE
      }
      ERROR -> {
        searchProgressBar.visibility = View.GONE
        showAlertDialog(getString(R.string.error), getString(R.string.fetch_error))
      }
    }
  }

  private fun renderSuccessResponse(response: List<GifItem>?) {
    response?.let { gifResponse ->
      searchRecyclerView.adapter = gifRecyclerViewAdapter
      searchRecyclerView.layoutManager =
        StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
      gifRecyclerViewAdapter.updateGifList(gifResponse)
    }
  }

  private fun showAlertDialog(
    title: String,
    message: String
  ) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)
        .setTitle(title)
        .setNeutralButton(R.string.ok, DialogInterface.OnClickListener { dialog, _ ->
          dialog.dismiss()
        })
        .create()
        .show()
  }
}