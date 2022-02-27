package com.example.pos2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel: ViewModel() {

    //Create an instance of livedata to hold certain type of data, in this case ,it's an Int
    val music_status : MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    //Getter
    fun getmusic_status(): LiveData<String>{
        return music_status
    }

//    var music_status = ""

}
