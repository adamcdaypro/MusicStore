package com.example.musicstore.services

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ITunesService {

    private const val ITUNES_URL = "https://itunes.apple.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(ITUNES_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    suspend fun lookupSongs(): Response<ITunesSongResults> {
        val service = retrofit.create(ITunesSongsLookupService::class.java)
        return service.lookupSongsById()
    }

    suspend fun lookupSongs(id: String): Response<ITunesSongResults> {
        val service = retrofit.create(ITunesSongsLookupService::class.java)
        return service.lookupSongsById(id)
    }

    suspend fun lookupAlbumById(id: String): Response<ITunesAlbumResults> {
        val service = retrofit.create(ITunesAlbumLookupService::class.java)
        return service.lookupAlbumById(id)
    }

    suspend fun searchAlbums(): Response<ITunesAlbumResults> {
        val service = retrofit.create(ITunesSearchService::class.java)
        return service.searchAlbumsByTerm()
    }

    suspend fun searchAlbums(query: String): Response<ITunesAlbumResults> {
        val service = retrofit.create(ITunesSearchService::class.java)
        return service.searchAlbumsByTerm(query)
    }
}

private interface ITunesSongsLookupService {

    @GET("lookup?id=580708374&entity=song")
    suspend fun lookupSongsById(): Response<ITunesSongResults>

    @GET("lookup?entity=song")
    suspend fun lookupSongsById(@Query("id") id: String): Response<ITunesSongResults>
}

private interface ITunesAlbumLookupService {

    @GET("lookup?entity=album")
    suspend fun lookupAlbumById(@Query("id") id: String): Response<ITunesAlbumResults>
}

private interface ITunesSearchService {

    @GET("search?term=led+zeppelin&entity=album")
    suspend fun searchAlbumsByTerm(): Response<ITunesAlbumResults>

    @GET("search?entity=album")
    suspend fun searchAlbumsByTerm(@Query("term") searchTerm: String): Response<ITunesAlbumResults>
}