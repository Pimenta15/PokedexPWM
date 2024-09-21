package com.example.pokedexpwm.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemon(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_table WHERE id = :pokemonId")
    suspend fun getPokemonById(pokemonId: Int): PokemonEntity?
}
