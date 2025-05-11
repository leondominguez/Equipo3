package com.univalle.dogapp.network

import retrofit2.Response
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getDogBreeds(): Response<DogBreedsResponse>

    @GET("breed/{breed}/images/random")
    suspend fun getRandomImageByBreed(@retrofit2.http.Path("breed") breed: String): Response<RandomImageResponse>
    @GET("breeds/image/random")
    suspend fun getRandomImage(): Response<RandomImageResponse>
}

data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)
data class RandomImageResponse(
    val message: String,
    val status: String
)