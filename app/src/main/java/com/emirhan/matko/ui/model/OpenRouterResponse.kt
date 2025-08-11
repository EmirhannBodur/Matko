package com.emirhan.matko.ui.model

import com.google.gson.annotations.SerializedName

data class OpenRouterResponse(
    val choices: List<Choice>
)
data class Choice(
    @SerializedName("message") val message: ChatMessage
)