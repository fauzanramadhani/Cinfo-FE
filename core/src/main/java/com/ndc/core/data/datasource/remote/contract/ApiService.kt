package com.ndc.core.data.datasource.remote.contract

import com.ndc.core.data.base.BaseResponse
import com.ndc.core.data.datasource.remote.body.AuthResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("/register")
    @FormUrlEncoded
    suspend fun registerUser(
        @Field("email") email: String
    ): BaseResponse<AuthResponse>
}