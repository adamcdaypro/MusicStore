package com.example.musicstore.services

import android.media.Image
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object ImageService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

interface ImageFetchService {

    @GET("{path}")
    suspend fun getImage(@Path("path") path: String): Response<Image>

}