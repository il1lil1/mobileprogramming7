package com.example.newsifyprac

import androidx.annotation.Nullable

class NewsData(var title:String, var url:String, var reporter:String, var date:String, var category:String, var broadcaster:String,  var memo : String="",var scraped : Boolean = false, var favorited : Boolean = false, var categorySelect : Boolean = true, var reporterSelect : Boolean = false, var broadcasterSelect : Boolean = true ) {

}