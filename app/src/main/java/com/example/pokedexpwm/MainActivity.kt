package com.example.pokedexpwm

import PokedexScreen
import PokemonViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedexpwm.Screens.PokemonDetailScreen
import com.example.pokedexpwm.Screens.easterEggScreen
import com.example.pokedexpwm.viewmodel.PokemonViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtém o DAO do banco de dados
        val pokemonDao = (application as PokedexApplication).database.pokemonDao()

        setContent {
            // Passa o DAO ao ViewModel
            val viewModel: PokemonViewModel = viewModel(
                factory = PokemonViewModelFactory(pokemonDao) // Usando Factory para passar o DAO
            )
            PokedexApp(viewModel = viewModel)
        }
    }
}

@Composable
fun PokedexApp(
    navController: NavHostController = rememberNavController(),
    viewModel: PokemonViewModel
) {
    NavHost(navController = navController, startDestination = "pokedexScreen") {
        composable("pokedexScreen") {
            PokedexScreen(navController = navController, viewModel = viewModel)
        }
        composable("pokemonDetailScreen") {
            PokemonDetailScreen(navController,viewModel = viewModel)
        }
        composable("easterEggScreen") {
            easterEggScreen(navController,viewModel = viewModel)
        }
    }
}
