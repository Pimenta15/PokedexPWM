package com.example.pokedexpwm

import PokemonViewModel
import android.content.ClipData.Item
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter // Para carregar imagens

import getTypeColor

@Composable
fun PokemonDetailScreen(navController: NavController, viewModel: PokemonViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val selectedPokemon by viewModel.selectedPokemon

    selectedPokemon?.let { pokemon ->

        // Usar Box para sobrepor a imagem de fundo, botão de voltar e detalhes
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagem de fundo (pokébola) com opacidade reduzida
            Image(
                painter = painterResource(id = R.drawable.pokebola),  // Substitua com o nome correto do seu arquivo
                contentDescription = "Pokebola de fundo",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.3f), // Define a opacidade
                contentScale = ContentScale.Crop  // Ajusta a imagem para cobrir toda a tela
            )

            // Conteúdo da tela com LazyColumn
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Garante que o Row ocupe a largura total
                        horizontalArrangement = Arrangement.Start // Alinha o conteúdo à esquerda
                    ) {
                    IconButton(
                        onClick = { navController.navigate("PokedexScreen") },
                        modifier = Modifier
                            .padding(16.dp) // Adiciona espaçamento
                            .graphicsLayer(alpha = 0.6f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.seta), // Substitua com o ícone de seta apropriado
                            contentDescription = "Voltar",
                            tint = Color.Red // Cor branca para combinar com o layout
                        )
                    }
                    }
                }// Botão de Voltar no canto superior esquerdo
                item {
                    // Imagem do Pokémon
                    val painter = rememberImagePainter(pokemon.imageUrl)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.size(300.dp) // Ajuste o tamanho da imagem
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nome e número da Pokédex
                    Text(
                        text = "# ${pokemon.id} \t \t ${pokemon.name.capitalize()}",
                        color = Color.White,
                        maxLines = 1,
                        modifier = Modifier
                            .background(
                                color = Color.DarkGray,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .padding(15.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Tipos do Pokémon
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        pokemon.types.forEach { type ->
                            val backgroundColor = getTypeColor(type)
                            Text(
                                text = type.capitalize(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                maxLines = 1,
                                modifier = Modifier
                                    .background(
                                        backgroundColor,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                    .padding(8.dp)
                                    .width(100.dp) // Tamanho fixo para o fundo
                            )
                            if (type != pokemon.types.last()) {
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }

                // Exibir cada flavor text em um quadro
                items(pokemon.flavor_text) { text ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = text.lowercase().capitalize(),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify,
                            fontSize = 16.sp
                        )
                    }
                }
            }


        }
    } ?: run {
        Text(text = "Nenhum Pokémon selecionado")
    }
}
