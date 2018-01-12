package com.kazakago.factolize.samplekotlin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.kazakago.factolize.Factory

@Factory
class SubViewModel(application: Application, intValue: Int, stringValue: String) : AndroidViewModel(application) {

    val intValueLiveData = MutableLiveData<Int>()
    val stringValueLiveData = MutableLiveData<String>()

    init {
        intValueLiveData.value = intValue
        stringValueLiveData.value = stringValue
    }

}

