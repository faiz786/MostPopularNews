package com.testapp.mostpopularnews.domain.usecases

import com.testapp.mostpopularnews.domain.models.News
import com.testapp.mostpopularnews.domain.models.OrderBy
import com.testapp.mostpopularnews.domain.repositories.NewsRepository
import com.testapp.mostpopularnews.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNews @Inject constructor(val newsRepository: NewsRepository) {

    operator fun invoke(query: String?, orderBy: OrderBy): Flow<DataState<Flow<List<News>>>> {
        return newsRepository.getNews(query, orderBy)
    }

}