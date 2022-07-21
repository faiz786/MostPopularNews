package com.testapp.mostpopularnews.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.testapp.mostpopularnews.data.entities.NewsDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_table ORDER BY publish_date desc")
    fun getAllNews() : LiveData<List<NewsDbEntity>>

    @Query("SELECT * FROM news_table WHERE id = :id")
    fun getNews(id: Int): LiveData<NewsDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsDbEntity: List<NewsDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(newsDbEntity: List<NewsDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsDbEntity: NewsDbEntity)



    @Query(
        "SELECT * FROM news_table WHERE title LIKE :query OR news_abstract LIKE :query OR category LIKE :query OR " +
                " source LIKE :query OR author LIKE :query ORDER BY publish_date desc"
    )
    fun getAllNewsWithQuery(
        query: String
    ): LiveData<List<NewsDbEntity>>

}