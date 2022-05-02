package com.inxparticle.quokkalabs.main

import com.inxparticle.quokkalabs.utils.Resource
import com.inxparticle.quokkalabs.data.model.BrakingBatApiResponse


interface MainRepository {

    suspend fun getCharacter(): Resource<ArrayList<BrakingBatApiResponse.BrakingBatApiResponseItem>>
}