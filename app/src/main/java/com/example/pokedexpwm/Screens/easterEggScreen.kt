import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedexpwm.R

@Composable
fun EasterEggScreen(navController: NavController, viewModel: PokemonViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    // Escolhendo um Pokémon aleatoriamente para o Easter Egg
    val randomPokemon = remember { viewModel.getRandomPokemon() }
    val selectedOption = remember { mutableStateOf<String?>(null) }
    val options = remember {
        val incorrectOptions = viewModel.getRandomIncorrectOptions(randomPokemon.name)
        listOf(randomPokemon.name, incorrectOptions[0], incorrectOptions[1]).shuffled()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
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
                        painter = painterResource(id = R.drawable.seta),
                        contentDescription = "Voltar",
                        tint = Color.Red
                    )
                }
            }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { navController.navigate("easterEggScreen") },
                modifier = Modifier
                    .padding(16.dp) // Adiciona espaçamento
                    .graphicsLayer(alpha = 0.6f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.recarregar),
                    contentDescription = "Voltar",
                    tint = Color.Red
                )
            }
        }
        }// Botão de Voltar no canto superior esquerdo
        // Imagem de fundo (pokébola) com opacidade reduzida
        Image(
            painter = painterResource(id = R.drawable.pokebola),  // Substitua com o nome correto do seu arquivo
            contentDescription = "Pokebola de fundo",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.3f), // Define a opacidade
            contentScale = ContentScale.Crop  // Ajusta a imagem para cobrir toda a tela
        )

        // Conteúdo principal
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // Pergunta
            Text(
                text = "Who's That Pokémon?",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            // Silhueta do Pokémon
            val painter = rememberAsyncImagePainter(randomPokemon.imageUrl)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(250.dp),
                colorFilter = if (selectedOption.value == null) ColorFilter.tint(Color.Black) else null, // Aplica um filtro de cor preta se não revelado
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Exibir as opções de escolha
            options.forEach { option ->
                val isCorrect = option == randomPokemon.name
                Button(
                    onClick = {
                        selectedOption.value = option
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            selectedOption.value == null -> Color.Gray
                            isCorrect -> Color.Green
                            else -> Color.Red
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = option.capitalize(),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

        }
    }




