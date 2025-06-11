package com.example.BookApp.models

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("biografi")
    val biografi: String?,

    @SerializedName("birth_date")
    val birthDate: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?
)
