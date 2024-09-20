package com.example.pokedexpwm.data.model

data class PokemonResponse(
    val name: String,
    val sprites: Sprites,
    val types: List<TypeResponse>
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class TypeResponse(
    val type: TypeDetails
)

data class TypeDetails(
    val name: String
)

data class Sprites(
    val front_default: String
)
data class PokemonListResponse(
    val results: List<PokemonResult>
)

