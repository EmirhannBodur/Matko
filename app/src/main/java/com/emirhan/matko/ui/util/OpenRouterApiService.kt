package com.emirhan.matko.ui.util

import com.emirhan.matko.ui.model.OpenRouterRequest
import com.emirhan.matko.ui.model.OpenRouterResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenRouterApiService {

    @POST("v1/chat/completions")
    suspend fun getChatRepsonse(
        @Header("Authorization") apiKey: String,
        @Body request: OpenRouterRequest
    ): OpenRouterResponse
}