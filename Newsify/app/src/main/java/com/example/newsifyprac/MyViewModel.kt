package com.example.newsifyprac

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel(){
    val selectedNum = MutableLiveData<Int>()

    var checkboxStates: MutableList<Boolean> = MutableList(11) { true }

    fun setLiveData(num:Int){
        selectedNum.value = num
    }

    val searchterm = MutableLiveData<String>()
    fun setLiveData(term:String){
        searchterm.value = term
    }
}