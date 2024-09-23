package com.example.pokedexpwm.Screens


import PokemonViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedexpwm.R

@Composable
fun easterEggScreen(navController: NavController, viewModel: PokemonViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var clickCount by remember { mutableStateOf(0) }
    Image(
        painter = painterResource(id = R.drawable.pokebola),  // Substitua com o nome correto do seu arquivo
        contentDescription = "Pokebola de fundo",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = 0.3f), // Define a opacidade
        contentScale = ContentScale.Crop  // Ajusta a imagem para cobrir toda a tela
    )
    Image(
        painter = painterResource(id = R.drawable.pokemonlogo),  // Substitua com o nome correto do seu arquivo
        contentDescription = "Pokemon Logo",
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                clickCount++
                if (clickCount == 3) {
                    clickCount = 0
                    navController.navigate("PokedexScreen")  // Navega para a tela de Easter Egg
                }
            }
            .padding(16.dp)
            .height(150.dp),  // Ajuste o tamanho conforme necessário
        contentScale = ContentScale.Fit
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Easter Egg",
            color = Color.Black,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
