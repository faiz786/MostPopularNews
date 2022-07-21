package com.testapp.mostpopularnews.data.repository

import androidx.lifecycle.LiveData
import com.testapp.mostpopularnews.data.entities.NewsDbEntity
import com.testapp.mostpopularnews.data.local.NewsDao
import com.testapp.mostpopularnews.data.models.NewsResponse
import com.testapp.mostpopularnews.data.remote.NewsRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsDao
) {


    suspend fun getAllNewsRemote():Response<NewsResponse?> // get news from remote
    {
       return remoteDataSource.getNewsFromRemote()
    }

    suspend fun getSearchQuery(query:String):Response<NewsResponse?> // get news from remote
    {
        return remoteDataSource.getNewsFromRemoteQuerySearch(query)
    }

    fun getSearchQueryFromDB(query:String):LiveData<List<NewsDbEntity>> // get news from remote
    {
        return localDataSource.getAllNewsWithQuery(query)
    }

    fun getAllNewsFromDB(): LiveData<List<NewsDbEntity>> {   // get news from database
        return localDataSource.getAllNews()
    }

    suspend fun insertAllNewsInDB(newsDbEntity: List<NewsDbEntity>)
    {
        localDataSource.insertAll(newsDbEntity)
    }

    suspend fun updateAllNewsInDB(newsDbEntity: List<NewsDbEntity>)
    {
        localDataSource.updateAll(newsDbEntity)
    }
}