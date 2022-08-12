package com.company.definelabstest.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.company.definelabstest.model.Matches

@Dao
interface MatchesDao {

    @Insert
    fun insertVenues(matches: Matches.Response.Venues)

    @Query(value = "SELECT * FROM venues")
    fun getVenues(): MutableList<Matches.Response.Venues>

    @Query(value = "DELETE FROM venues WHERE id = :id")
    fun deleteMatches(id: String)

    @Query("SELECT COUNT() FROM venues WHERE id = :id")
    fun count(id: String): Int

}