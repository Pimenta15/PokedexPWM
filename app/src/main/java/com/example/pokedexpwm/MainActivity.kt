package com.example.pokedexpwm

import PokedexScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedexpwm.ui.theme.PokedexPWMTheme
import com.example.pokedexpwm.viewmodel.PokemonViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexApp()
        }
    }
}

    @Composable
    fun PokedexApp(navController: NavHostController = rememberNavController(), viewModel: PokemonViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
        NavHost(navController = navController, startDestination = "pokedexScreen") {
            composable("pokedexScreen") {
                PokedexScreen(navController = navController, viewModel = viewModel)
            }
            composable("pokemonDetailScreen") {
                PokemonDetailScreen(viewModel = viewModel)
            }
        }
    }

