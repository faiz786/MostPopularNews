package com.testapp.mostpopularnews.domain.repositories

import com.testapp.mostpopularnews.domain.models.News
import com.testapp.mostpopularnews.domain.models.OrderBy
import com.testapp.mostpopularnews.utils.DataState
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(query: String?, orderBy: OrderBy): Flow<DataState<Flow<List<News>>>>

    suspend fun getNewsById(id: Long): Flow<DataState<News?>>

    suspend fun createNews(news: News): Flow<DataState<News>>
}