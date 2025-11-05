package com.example.listarolavel.data.remote.dto

import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface AlunoApi {
    @GET("alunos")
    suspend fun list(): List<AlunoDto>

    @GET("alunos/{id}")
    suspend fun get(@Path("id") id: String): AlunoDto

    @Multipart
    @POST("alunos")
    suspend fun create(
        @Part("dados") dadosJson: RequestBody
    ): AlunoDto

    @Multipart
    @PUT("alunos/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Part("dados") dadosJson: RequestBody
    ): AlunoDto

    @DELETE("alunos/{id}")
    suspend fun delete(@Path("id") id: String)
}