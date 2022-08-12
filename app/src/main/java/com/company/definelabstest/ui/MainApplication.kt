package com.company.definelabstest.ui

import android.app.Application
import com.company.definelabstest.api.ApiService
import com.company.definelabstest.api.RetrofitBuilder
import com.company.definelabstest.repository.MatchesRepository
import com.company.definelabstest.room.MatchesDatabase

class MainApplication : Application() {

    lateinit var repository: MatchesRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize(){
        val service = RetrofitBuilder.getInstance().create(ApiService::class.java)
        val database = MatchesDatabase.getDatabase(applicationContext)
        repository = MatchesRepository(service, database, applicationContext)
    }
}