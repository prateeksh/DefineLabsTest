package com.company.definelabstest.ui.allmatches.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.definelabstest.repository.MatchesRepository


class HomeViewModelFactory (private val repository: MatchesRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }

}