package com.testapp.mostpopularnews.data.remote

////import com.testapp.mostpopularnews.data.entities.CharacterList
//import com.testapp.mostpopularnews.data.entities.NewsDbEntity
import androidx.viewbinding.BuildConfig
import com.testapp.mostpopularnews.data.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsBackgroundService {
    @GET("/svc/mostpopular/v2/mostviewed/{newsType}/{period}.json")
    suspend fun getMostViewedNews(
        @Path("newsType") newsType: String = "all-sections",
        @Path("period") period: Int,
        @Query("api-key") apiKey: String = "CLXJpRmEttXp48ZNFE05rw9s2X8YglY3"
    ): Response<NewsResponse?>

    @GET("/svc/mostpopular/v2/mostviewed/{newsType}/{period}.json")
    suspend fun getMostViewedNewsQuerySearch(
        @Path("newsType") newsType: String,
        @Path("period") period: Int,
        @Query("api-key") apiKey: String = "CLXJpRmEttXp48ZNFE05rw9s2X8YglY3"
    ): Response<NewsResponse?>

//    @GET("character/{id}")
//    suspend fun getCharacter(@Path("id") id: Int): Response<Character>
}