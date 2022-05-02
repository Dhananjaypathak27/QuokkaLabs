package com.inxparticle.quokkalabs.main

import com.inxparticle.quokkalabs.utils.Resource
import com.inxparticle.quokkalabs.data.QuokkaLabsApi
import com.inxparticle.quokkalabs.data.model.BrakingBatApiResponse
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: QuokkaLabsApi
) : MainRepository {
    override suspend fun getCharacter(): Resource<ArrayList<BrakingBatApiResponse.BrakingBatApiResponseItem>> {
        return try{
            val response = api.getCharacters()
            val result = response.body()
            if(response.isSuccessful && result!=null){
                Resource.Success(result)
            }
            else{
                Resource.Error(response.message())
            }
        }catch (e:Exception){
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}