package com.testapp.mostpopularnews.di

import android.content.Context
import com.testapp.mostpopularnews.data.local.AppDatabase
import com.testapp.mostpopularnews.data.local.NewsDao
import com.testapp.mostpopularnews.data.remote.NewsRemoteDataSource
import com.testapp.mostpopularnews.data.remote.NewsBackgroundService
import com.testapp.mostpopularnews.data.repository.NewsRepository
import com.testapp.mostpopularnews.utils.GsonDateDeSerializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.nytimes.com/"

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson,okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(dateDeSerializer: GsonDateDeSerializer): Gson {
        return GsonBuilder().registerTypeAdapter(Date::class.java, dateDeSerializer).create()
    }

    @Provides
    fun provideNewsBackgroundService(retrofit: Retrofit): NewsBackgroundService = retrofit.create(NewsBackgroundService::class.java)

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsBackgroundService: NewsBackgroundService) = NewsRemoteDataSource(newsBackgroundService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: NewsRemoteDataSource,
                          localDataSource: NewsDao) =
        NewsRepository(remoteDataSource, localDataSource)
}