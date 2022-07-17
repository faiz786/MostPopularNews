package com.testapp.mostpopularnews.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.testapp.mostpopularnews.data.room.daos.NewsDao
import com.testapp.mostpopularnews.data.room.daos.NewsImageDao
import com.testapp.mostpopularnews.data.room.models.NewsImageInDb
import com.testapp.mostpopularnews.data.room.models.NewsInDb
import com.testapp.mostpopularnews.data.room.utils.DateConverter

@Database(entities = [NewsInDb::class, NewsImageInDb::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDao: NewsDao
    abstract val newsImageDao: NewsImageDao

    companion object {
        const val DATABASE_NAME = "news_db"
    }
}