package com.example.BookApp.models

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("id")
    val id: Int,

    @SerializedName("judul")
    val judul: String?,

    @SerializedName("author_id")
    val authorId: Int,

    @SerializedName("deskripsi")
    val deskripsi: String?,

    @SerializedName("rating")
    val rating: Float?,

    @SerializedName("tanggal_publikasi")
    val tanggalPublikasi: String?,

    @SerializedName("cover_image_url")
    val coverImageUrl: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?,

    @SerializedName("author")
    val author: Author?,

    @SerializedName("genres")
    val genres: List<Genre>?
)

