package com.company.definelabstest.ui.savedmatches.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.definelabstest.repository.MatchesRepository
import com.company.definelabstest.ui.allmatches.viewmodel.HomeViewModel


class SavedViewModelFactory (private val repository: MatchesRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SavedMatchesViewModel(repository) as T
    }

}