package com.example.pokedexpwm.data.network

import com.example.pokedexpwm.data.model.PokemonListResponse
import com.example.pokedexpwm.data.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: Int
    ): Response<PokemonDetailsResponse>
}

data class PokemonDetailsResponse(
    val types: List<TypeSlot>
)

data class TypeSlot(
    val type: Type
)

data class Type(
    val name: String
)
