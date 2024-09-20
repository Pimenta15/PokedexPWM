package com.example.pokedexpwm.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexpwm.data.model.Pokemon
import com.example.pokedexpwm.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    var pokemonList = mutableStateListOf<Pokemon>()
        private set

    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getPokemonList(limit = 151, offset = 0)
            if (response.isSuccessful) {
                response.body()?.results?.let { list ->
                    // Gerando a lista de Pokémon com nome e imagem
                    val updatedList = list.mapIndexed { index, pokemonResult ->
                        val pokemonId = index + 1
                        val pokemonDetails = RetrofitInstance.api.getPokemonDetails(pokemonId) // Chama os detalhes de cada Pokémon

                        if (pokemonDetails.isSuccessful) {
                            val types = pokemonDetails.body()?.types?.map { it.type.name } ?: listOf("unknown")
                            Pokemon(
                                name = pokemonResult.name,
                                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png",
                                types = types
                            )
                        } else {
                            Pokemon(
                                name = pokemonResult.name,
                                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png",
                                types = listOf("unknown")
                            )
                        }
                    }
                    pokemonList.addAll(updatedList)
                }
            }
        }
    }
}

