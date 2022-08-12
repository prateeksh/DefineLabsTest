package com.company.definelabstest.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.definelabstest.api.ApiService
import com.company.definelabstest.model.Matches
import com.company.definelabstest.room.MatchesDatabase
import com.company.definelabstest.utils.NetworkUtils

class MatchesRepository (
    private val apiService: ApiService,
    private val userDatabase: MatchesDatabase,
    private val applicationContext: Context
) {

    private val dataset = MutableLiveData<Matches>()
    private var listData = MutableLiveData<MutableList<Matches.Response.Venues>>()

    val savedList: LiveData<MutableList<Matches.Response.Venues>>
        get() = listData

    val users: LiveData<Matches>
        get() = dataset

    suspend fun getMatchesData() {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            val response = apiService.getMatches()
            if (response.body() != null)
            dataset.postValue(response.body())
        }
    }

    suspend fun getSavedMatchesFromDb(){
        listData.postValue( userDatabase.userDao().getVenues())
    }

    fun checkExistence(id: String): Boolean{
       val exists = userDatabase.userDao().count(id)
        return exists == 0
    }
}