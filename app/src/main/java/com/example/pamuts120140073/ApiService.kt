package com.example.pamuts120140073

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    fun getUsers(): Call<ApiResponse>

    // Menambahkan metode baru jika ingin mengambil data pengguna berdasarkan ID
    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Int): Call<User>
}