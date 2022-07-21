package com.testapp.mostpopularnews.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*


data class News(
    val id: Long,
    val title: String,
    val newsAbstract: String,
    val publishDate: Date,
    val category: String,
    val author: String,
    val source: String,
    val url: String,
    val images: String
):Serializable