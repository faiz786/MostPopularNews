package com.testapp.mostpopularnews.usecases

import com.testapp.mostpopularnews.data.repositories.NewsRepositoryImpl
import com.testapp.mostpopularnews.domain.models.OrderBy
import com.testapp.mostpopularnews.domain.usecases.GetNews
import com.testapp.mostpopularnews.utils.DataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetNewsTest {

    private lateinit var getNews: GetNews
    private lateinit var newsRepositoryImpl: NewsRepositoryImpl

    @Before
    fun setup() {
        newsRepositoryImpl = mockk(relaxed = true)
        getNews = GetNews(newsRepositoryImpl)

        coEvery {
            newsRepositoryImpl.getNews(
                any(),
                any()
            )
        } returns flow { emit(DataState.Loading()) }
    }

    @Test
    fun `calls repository get news with same query and order by`() = runBlockingTest {
        val query = null
        val orderBy = OrderBy.Date()
        getNews(query, orderBy).last()
        coVerify { newsRepositoryImpl.getNews(query, orderBy) }
    }
}