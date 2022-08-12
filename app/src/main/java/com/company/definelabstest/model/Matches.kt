package com.company.definelabstest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Matches (

    @SerializedName("response"      ) var response      : Response?                = Response()

){
    data class Response (
        @SerializedName("venues"    ) var venues    : ArrayList<Venues> = arrayListOf(),

    ){
        @Entity(tableName = "venues")
        data class Venues (

            @PrimaryKey
            @SerializedName("id"          ) var id          : String               ,
            @SerializedName("name"        ) var name        : String?               = null,
            @SerializedName("verified"    ) var verified    : Boolean?              = null,

        )
    }
}