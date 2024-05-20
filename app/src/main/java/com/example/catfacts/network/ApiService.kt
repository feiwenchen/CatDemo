package com.example.catfacts.network

import com.example.data_plugin.model.FactDetailResponse
import com.example.data_plugin.model.FactResponse
import retrofit2.http.*

interface ApiService {

    companion object {
        const val SERVER_URL = "https://cat-fact.herokuapp.com/"
    }

//    @GET("facts/random?animal_type=cat")
//    suspend fun getCatFacts(@Query("amount") pageSize: Int): ApiResponse<ApiPagerResponse<ArrayList<FactResponse>>>

    @GET("facts/random?animal_type=cat")
    suspend fun getCatFacts(@Query("amount") pageSize: Int): ArrayList<FactResponse>

    @GET("facts/{factId}")
    suspend fun getCatFactDetail(@Path("factId") factId: String): FactDetailResponse

}