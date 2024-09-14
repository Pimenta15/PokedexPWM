import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokedexpwm.viewmodel.PokemonViewModel

@Composable
fun PokedexScreen(viewModel: PokemonViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    // Obtendo a lista de Pokémon do ViewModel
    val pokemonList = viewModel.pokemonList

    // Usando LazyVerticalGrid para exibir os Pokémon em duas colunas
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Define duas colunas
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pokemonList) { pokemon ->
            PokemonItem(pokemon = pokemon)
        }
    }
}

@Composable
fun PokemonItem(pokemon: com.example.pokedexpwm.data.model.Pokemon) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
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

            // Nome do Pokémon
            Text(
                text = pokemon.name.capitalize(),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                maxLines = 1
            )
        }
    }
}


