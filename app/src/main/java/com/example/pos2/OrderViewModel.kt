package com.example.pos2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel: ViewModel() {

    //Create an instance of livedata to hold certain type of data, in this case ,it's an Int
    val totalAmount : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }

    //Getter
    fun getTotalAmount(): LiveData<Int>{
        return totalAmount
    }

//    var totalAmount : Int = 0

}
