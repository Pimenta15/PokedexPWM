package com.example.pokedexpwm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedexpwm.data.model.Pokemon
import com.example.pokedexpwm.viewmodel.PokemonViewModel

@Composable
fun PokedexScreen(viewModel: PokemonViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    // Observe a lista de Pokémon do ViewModel
    val pokemonList = viewModel.pokemonList

    // Exibe a lista de Pokémon usando LazyColumn
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(pokemonList.size) { index ->
            val pokemon = pokemonList[index]
            PokemonItem(pokemon)
        }
    }
}


@Composable
fun PokemonItem(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

    ) {
        Text(
            text = pokemon.name.capitalize(),
            modifier = Modifier.padding(16.dp)
        )
    }
}