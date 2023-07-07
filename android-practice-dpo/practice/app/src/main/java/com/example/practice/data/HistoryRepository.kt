package com.example.practice.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.practice.data.photo.HistoryPhoto
import com.example.practice.data.photo.HistoryPhotoDao
import com.example.practice.data.photo.HistoryUser
import com.example.practice.data.photo.PhotoItem
import com.example.practice.data.photo.Urls
import com.example.practice.data.photo.User
import com.example.practice.data.user.ProfileImage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class HistoryRepository(
    private val context: Context,
    private val historyPhotoDao: HistoryPhotoDao
) {

    suspend fun getHistoryPhoto(offset: Int): List<PhotoItem> {

        val tempList = ArrayList<PhotoItem>()

        historyPhotoDao.getHistoryPhoto(offset)
            .forEach {
                val tempUser = historyPhotoDao.getHistoryUser(it.id)
                val tempPhotoItem = PhotoItem(
                    it.id,
                    Urls(it.urls_image, it.urls_image),
                    it.likes,
                    it.liked_by_user,
                    User(
                        tempUser.id,
                        tempUser.username,
                        tempUser.name,
                        ProfileImage(tempUser.profile_image),
                        null
                    )
                )
                tempList.add(tempPhotoItem)
            }
        return tempList
    }

    suspend fun insertPhoto(photoItem: PhotoItem) {
        with(photoItem) {
            val tempPhoto = HistoryPhoto(
                id,
                likes,
                liked_by_user,
                saveImage(urls.small, id)!!,
                user.id
            )
            insertUser(user)
            return historyPhotoDao.insertPhoto(tempPhoto)
        }
    }

    private suspend fun insertUser(user: User) {
        with(user) {
            val tempUser = HistoryUser(
                id,
                username,
                name,
                saveImage(profile_image.small, id)!!,
            )
            return historyPhotoDao.insertUser(tempUser)
        }
    }

    suspend fun clean(){
        cleanHistoryPhoto()
        cleanHistoryUser()
    }

    private suspend fun cleanHistoryPhoto() {
        return historyPhotoDao.cleanHistoryPhoto()
    }

    private suspend fun cleanHistoryUser() {
        return historyPhotoDao.cleanHistoryUser()
    }

    private fun saveImage(imageUrl: String, imageName: String): String? {
        val url = URL(imageUrl)
        println(imageUrl)
        println(url.path)
        // Открываем соединение и устанавливаем метод запроса на GET
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        // Получаем код ответа от сервера
        val responseCode = connection.responseCode

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Получаем InputStream из соединения и создаем Bitmap изображения
            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Закрываем InputStream
            inputStream.close()

            // Получаем путь к внутренней директории приложения для сохранения файла
            val directory = context.filesDir
            // Создаем объект типа File с именем файла
            val file = File(directory, imageName)

            // Создаем FileOutputStream и сохраняем изображение в файл
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

            // Закрываем FileOutputStream
            outputStream.flush()
            outputStream.close()
            println(file.path)
            return file.path
        }
        return null
    }

}