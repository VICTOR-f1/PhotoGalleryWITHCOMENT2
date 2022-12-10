package com.bignerdranch.android.photogallery

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//В PhotoGalleryActivity будет размещен фрагмент
//PhotoGalleryFragment,
class PhotoGalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)
        //В файле PhotoGalleryActivity.kt измените код
        //функции onCreate(...), чтобы проверить, разместился ли
        //фрагмент в контейнере. Если нет, создайется экземпляр
        //PhotoGalleryFragment и добавьляется  в контейнер
        val isFragmentContainerEmpty =
            savedInstanceState == null
        //Коммит асинхронный
        //Вызов commit() не выполняет транзакцию немедленно. Скорее,
        // транзакция запланирована для выполнения в основном потоке
        // пользовательского интерфейса, как только она сможет это сделать.
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,
                    PhotoGalleryFragment.newInstance())
                .commit()
        }
    }
    //Как правило объекты-компаньоны используются для объявления
    // переменных и функций, к которым требуется обращаться без
    // создания экземпляра класса. Либо для объявления констант.
    // По сути они своего рода замена статическим членам класса
    companion object {
        fun newIntent(context: Context): Intent
        {
            return Intent(context, PhotoGalleryActivity::class.java)
        }
    }
}