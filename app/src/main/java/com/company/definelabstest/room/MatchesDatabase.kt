package com.company.definelabstest.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.company.definelabstest.model.Matches

@Database(entities = [Matches.Response.Venues::class], version = 1)
abstract class MatchesDatabase: RoomDatabase() {

    abstract fun userDao(): MatchesDao

    companion object{
        private var INSTANCE: MatchesDatabase ?= null

        fun getDatabase(context: Context): MatchesDatabase{

            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MatchesDatabase::class.java,
                    "matchDB"
                ).build()
            }

            return INSTANCE!!
        }
    }
}