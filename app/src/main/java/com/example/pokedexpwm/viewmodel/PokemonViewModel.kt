package com.example.pokedexpwm.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexpwm.data.model.Pokemon
import com.example.pokedexpwm.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    var pokemonList = mutableStateListOf<Pokemon>()
        private set

    var selectedPokemon = mutableStateOf<Pokemon?>(null)
        private set

    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getPokemonList(limit = 151, offset = 0)
            if (response.isSuccessful) {
                response.body()?.results?.let { list ->
                    val updatedList = list.mapIndexed { index, pokemonResult ->
                        val pokemonId = index + 1
                        val pokemonDetails = RetrofitInstance.api.getPokemonDetails(pokemonId)

                        if (pokemonDetails.isSuccessful) {
                            val types = pokemonDetails.body()?.types?.map { it.type.name }
                                ?: listOf("unknown")

                            // Buscar flavor text do Pokémon
                            val flavorTextResponse =
                                RetrofitInstance.api.getPokemonFlavorText(pokemonId)
                            val englishFlavorText = flavorTextResponse.flavor_text_entries
                                .filter { it.language.name == "en" }
                                .map { it.flavor_text }
                                .map {
                                    it.replace(
                                        "\\n|\\f|\\t".toRegex(),
                                        " "
                                    )
                                } // Substitui caracteres indesejados
                                .distinct() // Remove textos repetidos

                            Pokemon(
                                id = pokemonId,
                                name = pokemonResult.name,
                                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png",
                                types = types,
                                flavor_text = englishFlavorText // Atribuir flavor text
                            )
                        } else {
                            Pokemon(
                                id = pokemonId,
                                name = pokemonResult.name,
                                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png",
                                types = listOf("unknown"),
                                flavor_text = listOf("Texto não disponível")
                            )
                        }
                    }
                    pokemonList.addAll(updatedList)
                }
            }
        }
    }
    // Define o Pokémon selecionado
    fun selectPokemon(pokemon: Pokemon) {
        selectedPokemon.value = pokemon // Atualiza a variável selectedPokemon
    }
}



