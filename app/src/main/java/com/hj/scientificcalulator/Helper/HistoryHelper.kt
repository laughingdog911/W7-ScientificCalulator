package com.hj.scientificcalulator.Helper

import androidx.lifecycle.MutableLiveData
import com.hj.scientificcalulator.Models.HistoryDataModel

class HistoryHelper {

    companion object{
        var historyList = MutableLiveData<MutableList<HistoryDataModel>>().apply {
            value = mutableListOf()
        }
    }
}