package com.example.newsifyprac

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel(){
    val selectedNum = MutableLiveData<Int>()
    fun setLiveData(num:Int){
        selectedNum.value = num
    }
}