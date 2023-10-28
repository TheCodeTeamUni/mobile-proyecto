package com.example.vinilos.data.api


import com.example.vinilos.LoginBody
import com.example.vinilos.data.model.*
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("users/validate")
    suspend fun validateEmailAddress(@Body body: ValidateEmailBody): Response<UniqueEmailValidationResponse>

   // @POST("users/signup")
    //suspend fun registerUser(@Body body: RegisterBody): Response<AuthResponse>

    @POST("users/signup")
    suspend fun registerUser(@Body body: RegisterBody): Response<RegisterResponse>

    @POST("users/login")
    suspend fun loginUser(@Body body: LoginBody): Response<AuthResponse>

    @GET("albums")
    suspend fun getAlbums(): List<AlbumResponse>

    @GET("albums/{id}")
    suspend fun getAlbumDetail(@Path("id") id: String): AlbumResponse

    @GET("albums")
    suspend fun getAllAlbums(): Response<List<AlbumResponse>>

    @POST("albums")
    suspend fun createAlbum(
        @Body album: HashMap<String, String>
    ): Response<AlbumResponse>

    @GET("musicians")
    suspend fun getMusicians(): List<ArtistResponse>

    @GET("artist")
    suspend fun getAllArtist(): Response<List<ArtistResponse>>

    @GET("bands")
    suspend fun getBands(): List<ArtistResponse>

    @GET("collectors")
    suspend fun getCollectors(): List<CollectorResponse>

    @GET("collectors")
    suspend fun getAllCollectors(): Response<List<CollectorResponse>>

    @GET("musicians/{id}")
    suspend fun getMusiciansDetail(@Path("id") id: String): ArtistResponse

    @GET("bands/{id}")
    suspend fun getBandsDetail(@Path("id") id: String): ArtistResponse

    @GET("collectors/{id}")
    suspend fun getCollectorsDetail(@Path("id") id: String): CollectorResponse

    @POST("albums/{id}/tracks")
    suspend fun postAlbumTrack(@Path("id") id: String, @Body track: JsonObject): AlbumResponse

    @POST("albums")
    suspend fun postAlbum(@Body album: JsonObject): AlbumResponse

}