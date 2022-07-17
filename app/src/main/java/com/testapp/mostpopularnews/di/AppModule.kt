package com.testapp.mostpopularnews.di

import android.content.Context
import com.testapp.mostpopularnews.data.repositories.NewsRepositoryImpl
import com.testapp.mostpopularnews.data.retrofit.NyTimesApi
import com.testapp.mostpopularnews.data.retrofit.RetrofitNewsMapper
import com.testapp.mostpopularnews.data.room.RoomNewsMapper
import com.testapp.mostpopularnews.data.room.daos.NewsDao
import com.testapp.mostpopularnews.data.room.daos.NewsImageDao
import com.testapp.mostpopularnews.domain.repositories.NewsRepository
import com.testapp.mostpopularnews.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideDispatchers(): AppDispatchers {
        return object : AppDispatchers {
            override fun main(): CoroutineDispatcher {
                return Dispatchers.Main
            }

            override fun default(): CoroutineDispatcher {
                return Dispatchers.Default
            }

            override fun io(): CoroutineDispatcher {
                return Dispatchers.IO
            }

        }
    }


    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideNewsRepository(
        retrofitNewsMapper: RetrofitNewsMapper, nyTimesApi: NyTimesApi,
        roomNewsMapper: RoomNewsMapper,
        newsDao: NewsDao, newsImageDao: NewsImageDao,
        appDispatchers: AppDispatchers, @ApplicationContext context: Context
    ): NewsRepository {
        return NewsRepositoryImpl(
            retrofitNewsMapper,
            nyTimesApi,
            roomNewsMapper,
            newsDao,
            newsImageDao,
            appDispatchers,
            context
        )
    }
}