package com.example.pokedexpwm.viewmodel

import PokemonViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedexpwm.DataBase.PokemonDao


class PokemonViewModelFactory(
    private val pokemonDao: PokemonDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            return PokemonViewModel(pokemonDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
