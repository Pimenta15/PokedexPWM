package com.example.pokedexpwm.data.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>, // Lista de tipos do Pokémon
    val flavor_text: List<String> // Lista de flavor text do Pokémon
)

