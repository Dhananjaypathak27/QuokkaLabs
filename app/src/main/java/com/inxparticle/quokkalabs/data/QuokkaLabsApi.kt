package com.inxparticle.quokkalabs.data

import com.inxparticle.quokkalabs.data.model.BrakingBatApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface QuokkaLabsApi {

    @GET("api/characters")
    suspend fun getCharacters(
    ): Response<ArrayList<BrakingBatApiResponse.BrakingBatApiResponseItem>>

}