package com.testapp.mostpopularnews.data.remote

import com.testapp.mostpopularnews.utils.Resource
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsBackgroundService: NewsBackgroundService
): BaseDataSource() {

//    suspend fun getNewsFromRemote():Resource<T> = getResult { characterService.getAllCharacters() }

    suspend fun getNewsFromRemote() = newsBackgroundService.getMostViewedNews(period = 7)

    suspend fun getNewsFromRemoteQuerySearch(query:String) = newsBackgroundService.getMostViewedNewsQuerySearch(newsType = query, period = 7)
}