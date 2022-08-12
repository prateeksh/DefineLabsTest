package com.company.definelabstest.ui.savedmatches.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.definelabstest.model.Matches
import com.company.definelabstest.repository.MatchesRepository
import com.company.definelabstest.room.MatchesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedMatchesViewModel(private val repository: MatchesRepository) : ViewModel() {

    fun init() {
        viewModelScope.launch (Dispatchers.IO){
            repository.getSavedMatchesFromDb()
        }

    }

    val saved: LiveData<MutableList<Matches.Response.Venues>>
        get() = repository.savedList
}