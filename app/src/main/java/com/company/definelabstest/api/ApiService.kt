package com.company.definelabstest.api

import com.company.definelabstest.model.Matches
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("search?ll=40.7484,-73.9857&oauth_token=NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ&v=20180616")
    suspend fun getMatches(): Response<Matches>

}