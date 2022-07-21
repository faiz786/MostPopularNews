package com.testapp.mostpopularnews.ui.newsListing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.testapp.mostpopularnews.data.entities.NewsDbEntity
import com.testapp.mostpopularnews.data.repository.NewsRepository
import com.testapp.mostpopularnews.utils.Resource
import com.testapp.mostpopularnews.utils.Resource1
import kotlinx.coroutines.Dispatchers

class NewsListingViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    fun getNewsFromRemote() =  liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getAllNewsRemote()))// get from remote
        }catch (e:Exception)
        {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }

    }

//    fun getNewsFromRemote() =  liveData(Dispatchers.IO){
//        emit(Resource.loading(data = null))
//        try {
//            emit(Resource1.Success(Resource1.Status.SUCCESS,data = repository.getAllNewsRemote()))// get from remote
//        }catch (e:Exception)
//        {
//            emit(Resource1.Failure(Resource1.Status.ERROR,throwable = e.cause ?: Throwable("Error Occurred"),data = null ))
//        }

//    }

    fun getNewsFromRemoteQuerySearch(query:String) :LiveData<List<NewsDbEntity>>{
        return repository.getSearchQueryFromDB(query)// get from database
    }

    fun getNewsFromDatabase():LiveData<List<NewsDbEntity>>
    {
       return repository.getAllNewsFromDB()// get from database
    }

    suspend fun insertAllNewsInDB(newsDBEntity:List<NewsDbEntity>)
    {
        return repository.insertAllNewsInDB(newsDBEntity)// get from database
    }

    suspend fun updateAllNewsInDB(newsDBEntity:List<NewsDbEntity>)
    {
        return repository.insertAllNewsInDB(newsDBEntity)// get from database
    }

}
