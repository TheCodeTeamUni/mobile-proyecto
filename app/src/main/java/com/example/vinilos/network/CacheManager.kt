package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.data.model.AlbumResponse
import com.example.vinilos.data.model.ArtistResponse
import com.example.vinilos.data.model.CollectorResponse

class CacheManager(context: Context) {
    companion object {
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    //Albums Cache elements
    private var albums: HashMap<Int, AlbumResponse> = hashMapOf()
    fun addAlbum(albumId: Int, albumes: AlbumResponse) {
        if (!albums.containsKey(albumId)) {
            albums[albumId] = albumes
        }
    }

    fun getAlbum(albumId: Int): AlbumResponse? {
        return if (albums.containsKey(albumId)) albums[albumId]!! else null
    }

    //Artist cache elements
    private var artists: HashMap<Int, ArtistResponse> = hashMapOf()
    fun addArtist(artistId: Int, artista: ArtistResponse) {
        if (!artists.containsKey(artistId)) {
            artists[artistId] = artista
        }
    }

    fun getArtist(artistId: Int): ArtistResponse? {
        return if (artists.containsKey(artistId)) artists[artistId]!! else null
    }

    //Collectors Cache Elements
    private var collectors: HashMap<Int, CollectorResponse> = hashMapOf()
    fun addCollector(collectorId: Int, coleccionistas: CollectorResponse) {
        if (!collectors.containsKey(collectorId)) {
            collectors[collectorId] = coleccionistas
        }
    }

    fun getCollectors(collectorId: Int): CollectorResponse? {
        return if (collectors.containsKey(collectorId)) collectors[collectorId]!! else null
    }
}