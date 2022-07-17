package com.testapp.mostpopularnews.data.room.daos

import androidx.room.Dao
import androidx.room.Query
import com.testapp.mostpopularnews.data.room.models.NewsImageInDb

@Dao
abstract class NewsImageDao : BaseDao<NewsImageInDb>() {

    @Query("DELETE FROM news_image")
    abstract fun deleteAllNewsImages()
}