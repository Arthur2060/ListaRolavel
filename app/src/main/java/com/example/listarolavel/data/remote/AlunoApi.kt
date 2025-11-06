package com.example.listarolavel.data.remote


import com.example.listarolavel.data.remote.dto.AlunoDto
import okhttp3.RequestBody
import retrofit2.http.*

interface AlunoApi {
    @GET("usuarios")
    suspend fun list(): List<AlunoDto>

    @GET("usuarios/{id}")
    suspend fun get(@Path("id") id: String): AlunoDto

    @Multipart
    @POST("usuarios")
    suspend fun create(
        @Part("dados") dadosJson: RequestBody
    ): AlunoDto

    @Multipart
    @PUT("usuarios/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Part("dados") dadosJson: RequestBody
    ): AlunoDto

    @DELETE("usuarios/{id}")
    suspend fun delete(@Path("id") id: String)
}
