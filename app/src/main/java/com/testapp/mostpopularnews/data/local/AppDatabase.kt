package com.testapp.mostpopularnews.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.testapp.mostpopularnews.data.entities.NewsDbEntity
import com.testapp.mostpopularnews.utils.DateConverter

@Database(entities = [NewsDbEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): NewsDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "NewsDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }

}