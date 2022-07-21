package com.testapp.mostpopularnews.utils

sealed class Resource1<out R> {
    data class Success<out T>(val status:Status,val data: T) : Resource1<T>()
    data class Failure<out T>(val status:Status,val throwable: Throwable, val data: T? = null) : Resource1<T>()
    data class Loading<out T>(val status:Status,val data: T? = null) : Resource1<T>()

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}