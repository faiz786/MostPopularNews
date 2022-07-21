package com.testapp.mostpopularnews.ui.newsDetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.testapp.mostpopularnews.data.repository.NewsRepository

class NewsDetailViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

}
