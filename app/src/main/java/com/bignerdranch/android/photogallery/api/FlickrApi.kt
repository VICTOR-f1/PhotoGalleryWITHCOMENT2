package com.bignerdranch.android.photogallery.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface FlickrApi {
//Каждая функция в интерфейсе привязывается к
//конкретному HTTP-запросу и должна быть аннотирована
//аннотацией метода HTTP-запроса. Аннотация метода HTTPзапроса сообщает Retrofit тип HTTP-запроса (его называют
//HTTP verb),По умолчанию все веб-запросы Retrofit возвращают объект
//retrofit2.Call. Объект Call представляет собой один вебзапрос, который вы можете выполнить.

    @GET("services/rest/?method=flickr.interestingness.getList" + "&api_key=56bbc5d158e5c92dc817ab2c313e1dd4\n")
   //абстрактные методы
    fun fetchPhotos(): Call<FlickrResponse>
    @GET
    //Новая функция API выглядит слегка иначе. На вход ей
    //подается URL-адрес, который используется для определения
    //того, откуда загружать данные. Использование
    //беспараметрической аннотации @GET в сочетании с аннотацией
    //первого параметра в fetchUrlBytes(...) с @Url приводит к
    //тому, что Retrofit полностью переопределяет базовый URL.
    //Вместо этого Retrofit будет использовать URL, переданный в
    //функцию fetchUrlBytes(...).
    //Добавьте функцию в FlickrFetchr, чтобы загружать
    //данные по заданномуURL-адресу и декодировать их в
    //изображение Bitmap
    fun fetchUrlBytes(@Url url: String): Call<ResponseBody>
    @GET("services/rest?method=flickr.photos.search")
    fun searchPhotos(@Query("text") query: String): Call<FlickrResponse>
}

