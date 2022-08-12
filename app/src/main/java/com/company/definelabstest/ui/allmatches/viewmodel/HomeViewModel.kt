package com.company.definelabstest.ui.allmatches.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.definelabstest.model.Matches
import com.company.definelabstest.repository.MatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: MatchesRepository) : ViewModel() {

    //Dispatcher.IO is used to block the threads for IO operation (kotlin coroutines)
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMatchesData()
        }

    }

    val user: LiveData<Matches>
        get() = repository.users
}