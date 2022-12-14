package com.bignerdranch.android.photogallery.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.photogallery.PhotoResponse
import com.bignerdranch.android.photogallery.api.FlickrApi
import com.bignerdranch.android.photogallery.api.FlickrResponse
import com.bignerdranch.android.photogallery.api.PhotoInterceptor
import com.bignerdranch.android.photogallery.data.GalleryItem
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "FlickrFetchr"
class FlickrFetchr {
    private val flickrApi: FlickrApi
    //Основной конструктор не может в себе содержать какую-либо логику
    // по инициализации свойств (исполняемый код). Он предназначен исключительно
    // для объявления свойств и присвоения им полученных значений. Поэтому вся
    // логика может быть помещена в блок инициализаци
    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()
        val retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).client(client).build()
        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    fun fetchPhotosRequest(): Call<FlickrResponse> {
        return flickrApi.fetchPhotos()
    }

    fun searchPhotosRequest(query: String):
            Call<FlickrResponse> {
        return flickrApi.searchPhotos(query)
    }
// Функция fetchPhotos() теперь должна возвращать объект LiveData
//    LiveData умеет определять активен подписчик или нет, и отправлять
//    данные будет только активным подписчикам. Предполагается, что подписчиками LiveData будут Activity и фрагменты. А
// их состояние активности будет определяться с помощью их Lifecycle объекта, который мы рассмотрели в прошлом уроке.
    fun fetchPhotos(): LiveData<List<GalleryItem>> {
        return fetchPhotoMetadata(fetchPhotosRequest())
    }
// Функции fetchPhotos()
//и searchPhotos()выполняют запросы асинхронно и
//доставляют результаты, используя LiveData
    fun searchPhotos(query: String): LiveData<List<GalleryItem>> {
        return fetchPhotoMetadata(searchPhotosRequest(query))
    }

    private fun fetchPhotoMetadata(flickrRequest: Call<FlickrResponse>): LiveData<List<GalleryItem>> {
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()
        flickrRequest.enqueue(object : Callback<FlickrResponse> {
            override fun onFailure(call: Call<FlickrResponse>, t: Throwable
            )
            {
                Log.e(TAG, "Failed to fetchphotos", t)
            }

            override fun onResponse(
                call: Call<FlickrResponse>,
                response: Response<FlickrResponse>
            )
            {
                Log.d(TAG, "Response received")
                val flickrResponse: FlickrResponse? = response.body()
                val photoResponse: PhotoResponse? = flickrResponse?.photos
                var galleryItems: List<GalleryItem> = photoResponse?.galleryItems ?: mutableListOf()
                galleryItems = galleryItems.filterNot {
                        it.url.isBlank()
                    }
                responseLiveData.value = galleryItems
            }
        })
        return responseLiveData
    }
//Добавьте функцию в FlickrFetchr, чтобы загружать
//данные по заданномуURL-адресу и декодировать их в
//изображение
    // Аннотация
//@WorkerThread указывает, что эта функция должна
//вызываться только в фоновом потоке.
    @WorkerThread
    fun fetchPhoto(url: String): Bitmap? {
        val response: Response<ResponseBody> =
            flickrApi.fetchUrlBytes(url).execute()
        val bitmap =
            response.body()?.byteStream()?.use(BitmapFactory::decodeStream)
        Log.i(TAG,
            "Decoded bitmap=$bitmap from Response=$response")
        return bitmap
    }
}