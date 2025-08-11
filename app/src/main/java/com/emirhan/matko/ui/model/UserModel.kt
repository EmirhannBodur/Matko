package com.emirhan.matko.ui.model

data class UserModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val selectedExam: String? = null,          // Örn: "DGS"
    val examSubCategory: String? = null,       // Örn: "Ön Lisans" (sadece KPSS-B için)
    val mathLevel: String? = null,             // "Temel", "Orta", "İleri"
    val timestamp: Long = System.currentTimeMillis()
)
