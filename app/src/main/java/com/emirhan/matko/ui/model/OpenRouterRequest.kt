package com.emirhan.matko.ui.model

data class OpenRouterRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val system: Boolean=false
)
