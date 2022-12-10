package com.bignerdranch.android.photogallery.data

import android.net.Uri
import com.google.gson.annotations.SerializedName
//Идентификатор фотографии совпадает со значением
//атрибута photo_id в разметке JSON. Мы уже сохранили его в
//поле id объекта GalleryItem.? Немного покопавшись в документации, мы
//находим, что атрибут owner в JSON содержит идентификатор
//пользователя. Таким образом, извлекая атрибут owner, мы
//можем построить URL-адрес по атрибутам из JSON фотографии:
//https://www.flickr.com/photos/владелец/
//идентификатор
data class GalleryItem(var title: String = "",
                       var id: String = "",
                       //По умолчанию Gson
    //сопоставляет имена JSON-объектов с именами свойств. Если
    //имена ваших свойств совпадают с именами JSON-объектов, вы
    //можете оставить их как есть.
    //Однако имена свойств не всегда совпадают с именами JSON объектов. Возьмите свойство GalleryItem.url и поле данных
    //"url_s". Имя GalleryItem.url более значимо в контексте
    //вашей кодовой базы, поэтому лучше оставить его. В этом случае
    //вы можете добавить в свойство @SerializedNameannotation,
    //чтобы сообщить Gson, к какому JSON-полю относится свойство.
                       @SerializedName("url_s") var url: String = "",
                       @SerializedName("owner") var owner: String = "")
{
    //Идентификатор фотографии совпадает со значением
    //атрибута photo_id в разметке JSON. Мы уже сохранили его в
    //поле id объекта GalleryItem. Как насчет идентификатора
    //пользователя? Немного покопавшись в документации, мы
    //находим, что атрибут owner в JSON содержит идентификатор
    //пользователя. Таким образом, извлекая атрибут owner, мы
    //можем построить URL-адрес по атрибутам из JSON фотографии:
    val photoPageUri: Uri
        get() {
            return Uri.parse("https://www.flickr.com/photos/")
                .buildUpon()
                .appendPath(owner)
                .appendPath(id)
                .build()
        }
}