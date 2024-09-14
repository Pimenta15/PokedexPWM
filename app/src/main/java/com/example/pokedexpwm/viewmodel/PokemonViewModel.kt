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
            val response = RetrofitInstance.api.getPokemonList(limit = 151, offset = 0) //905 / 1025
            if (response.isSuccessful) {
                response.body()?.results?.let { list ->
                    // Gerando a lista de Pokémon com o nome e a URL da imagem
                    val updatedList = list.mapIndexed { index, pokemonResult ->
                        val pokemonId = index + 1
                        Pokemon(
                            name = pokemonResult.name,
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
                        )
                    }
                    pokemonList.addAll(updatedList)
                }
            }
        }
    }
}