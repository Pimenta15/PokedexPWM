import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pokedexpwm.R

@Composable
fun PokedexScreen(navController: NavController, viewModel: PokemonViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val pokemonList = viewModel.pokemonList
    val isLoading = viewModel.isLoading.value

    // Estado para contar os cliques no logo
    var clickCount by remember { mutableStateOf(0) }

    if (isLoading) {
        // Exibir a tela de carregamento
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokemonlogo),  // Substitua com o nome correto do seu arquivo
                contentDescription = "Pokemon Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(alpha = 1.0f)
                    .padding(16.dp)
                    .height(150.dp),  // Ajuste o tamanho conforme necessário
                contentScale = ContentScale.Fit
            )

            // Imagem de fundo (pokébola) com opacidade reduzida
            Image(
                painter = painterResource(id = R.drawable.pokebola),  // Substitua com o nome correto do seu arquivo
                contentDescription = "Pokebola de fundo",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.3f), // Define a opacidade
                contentScale = ContentScale.Crop  // Ajusta a imagem para cobrir toda a tela
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
    } else {
        // Imagem de fundo (pokébola) com opacidade reduzida
        Image(
            painter = painterResource(id = R.drawable.pokebola),  // Substitua com o nome correto do seu arquivo
            contentDescription = "Pokebola de fundo",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.3f), // Define a opacidade
            contentScale = ContentScale.Crop  // Ajusta a imagem para cobrir toda a tela
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Adiciona a logo como um item que ocupa toda a largura da tela
            item(span = { GridItemSpan(2) }) {
                Image(
                    painter = painterResource(id = R.drawable.pokemonlogo),  // Substitua com o nome correto do seu arquivo
                    contentDescription = "Pokemon Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            clickCount++
                            if (clickCount == 10) {
                                clickCount = 0
                                navController.navigate("easterEggScreen")  // Navega para a tela de Easter Egg
                            }
                        }
                        .padding(16.dp)
                        .height(150.dp),  // Ajuste o tamanho conforme necessário
                    contentScale = ContentScale.Fit
                )
            }

            // Lista de Pokémons
            items(pokemonList) { pokemon ->
                PokemonItem(pokemon = pokemon, onClick = {
                    viewModel.selectPokemon(pokemon)  // Seleciona o Pokémon
                    navController.navigate("pokemonDetailScreen")  // Navega para a tela de detalhes
                })
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: com.example.pokedexpwm.data.model.Pokemon, onClick: () -> Unit) {
    val backgroundColor = getTypeColor(pokemon.types.firstOrNull() ?: "unknown")

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            // Imagem do Pokémon
            Image(
                painter = rememberImagePainter(data = pokemon.imageUrl),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(128.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )

            // Nome do Pokémon com o número (#Número Nome)
            Text(
                text = "# ${pokemon.id} \t ${pokemon.name.capitalize()}",
                color = Color.White,
                maxLines = 1,
                modifier = Modifier
                    .background(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

fun getTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "fire" -> Color(0xFFCE4545)  // Laranja/Vermelho para fogo
        "water" -> Color(0xFF42A5F5)  // Azul para água
        "grass" -> Color(0xFF66BB6A)  // Verde para grama
        "electric" -> Color(0xFFFFC73B)  // Amarelo para elétrico
        "ice" -> Color(0xFF80DEEA)  // Azul Claro para gelo
        "fighting" -> Color(0xFFE57373)  // Vermelho para lutador
        "poison" -> Color(0xFFBA68C8)  // Roxo para veneno
        "ground" -> Color(0xFFC26236)  // Marrom para terra
        "flying" -> Color(0xFF90CAF9)  // Azul claro para voador
        "psychic" -> Color(0xFFFF4081)  // Rosa para psíquico
        "bug" -> Color(0xFFAED581)  // Verde Claro para inseto
        "rock" -> Color(0xFFBCAAA4)  // Cinza para pedra
        "ghost" -> Color(0xFF9575CD)  // Roxo para fantasma
        "dragon" -> Color(0xFF7E57C2)  // Roxo escuro para dragão
        "dark" -> Color(0xFF616161)  // Cinza Escuro para noturno
        "steel" -> Color(0xFFB0BEC5)  // Cinza metálico para aço
        "fairy" -> Color(0xFFF48FB1)  // Rosa claro para fada
        else -> Color(0xFF9E9E9E)  // Cinza padrão para tipos desconhecidos
    }
}
