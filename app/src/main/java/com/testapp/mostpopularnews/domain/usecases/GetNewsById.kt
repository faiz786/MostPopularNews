package com.testapp.mostpopularnews.domain.usecases

import com.testapp.mostpopularnews.domain.models.News
import com.testapp.mostpopularnews.domain.repositories.NewsRepository
import com.testapp.mostpopularnews.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsById @Inject constructor(val newsRepository: NewsRepository) {

    suspend operator fun invoke(id: Long): Flow<DataState<News?>> {
        return newsRepository.getNewsById(id)
    }

}