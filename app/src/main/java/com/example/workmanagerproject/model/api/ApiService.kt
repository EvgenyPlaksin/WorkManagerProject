package com.example.workmanagerproject.model.api

import com.example.workmanagerproject.model.response.ApiResponse
import com.example.workmanagerproject.model.utils.Constants
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.RANDOM_URL)
    fun getData(): Call<ApiResponse>

}