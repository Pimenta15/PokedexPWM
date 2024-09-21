package com.example.pokedexpwm.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>, // Pode ser uma string JSON para Room
    val flavorText: List<String> // Lista de flavor text do Pokémon
)
