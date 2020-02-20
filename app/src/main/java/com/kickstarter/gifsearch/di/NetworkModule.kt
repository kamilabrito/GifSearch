package com.kickstarter.gifsearch.di

import androidx.lifecycle.ViewModelProvider
import com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kickstarter.gifsearch.network.RetrofitInterface
import com.kickstarter.gifsearch.repository.GifRepository
import com.kickstarter.gifsearch.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
class NetworkModule {

  companion object {
    const val HEADER_TYPE_CONTENT = "Content-Type"
    const val HEADER_VALUE_TYPE_CONTENT = "application/json"
    const val HEADER_API_KEY = "api-key"
    const val HEADER_VALUE_API_KEY = "229ac3e932794695b695e71a9076f4e5"
    const val BASE_URL = "https://api.giphy.com"
  }

  @Provides
  @Singleton
  fun providesGson(): Gson {
    return GsonBuilder()
        .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
        .setLenient()
        .create()
  }

  @Provides
  @Singleton
  fun providesRetrofit(
    gson: Gson,
    okHttpClient: OkHttpClient
  ): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
  }

  @Provides
  @Singleton
  fun getRetrofitInterface(retrofit: Retrofit): RetrofitInterface {
    return retrofit.create(RetrofitInterface::class.java)
  }

  @Provides
  @Singleton
  fun getRequestHeader(): OkHttpClient {

    val httpClient = OkHttpClient.Builder()
    val interceptor = Interceptor { chain ->
      val original = chain.request()
      val request = original.newBuilder()
          .header(HEADER_TYPE_CONTENT, HEADER_VALUE_TYPE_CONTENT)
          .header(HEADER_API_KEY, HEADER_VALUE_API_KEY)
          .build()
      chain.proceed(request)
    }
    httpClient.addInterceptor(interceptor)
        .connectTimeout(100, SECONDS)
        .writeTimeout(100, SECONDS)
        .readTimeout(100, SECONDS)
    return httpClient.build()
  }

  @Provides
  @Singleton
  fun getRepository(retrofitInterface: RetrofitInterface): GifRepository {
    return GifRepository(retrofitInterface)
  }

  @Provides
  @Singleton
  fun getViewModelFactory(repository: GifRepository): ViewModelProvider.Factory {
    return ViewModelFactory(repository)
  }
}