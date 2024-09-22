import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexpwm.DataBase.PokemonDao
import com.example.pokedexpwm.DataBase.PokemonEntity
import com.example.pokedexpwm.data.model.Pokemon
import com.example.pokedexpwm.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonDao: PokemonDao) : ViewModel() {

    var pokemonList = mutableStateListOf<Pokemon>()
        private set

    var selectedPokemon = mutableStateOf<Pokemon?>(null)
        private set

    // Estado de carregamento
    var isLoading = mutableStateOf(true)
        private set
    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        val limit = 151
        viewModelScope.launch {
            // Tenta carregar os Pokémon do banco de dados
            try {


                val cachedPokemon = pokemonDao.getAllPokemon()

                if (cachedPokemon.isEmpty()|| cachedPokemon.size < limit) {
                    // Se o banco estiver vazio, pega da API
                    val response = RetrofitInstance.api.getPokemonList(limit = limit, offset = 0)
                    if (response.isSuccessful) {
                        response.body()?.results?.let { list ->
                            val updatedList = list.mapIndexed { index, pokemonResult ->
                                val pokemonId = index + 1
                                val pokemonDetails =
                                    RetrofitInstance.api.getPokemonDetails(pokemonId)

                                if (pokemonDetails.isSuccessful) {
                                    val types = pokemonDetails.body()?.types?.map { it.type.name }
                                        ?: listOf("unknown")

                                    val flavorTextResponse =
                                        RetrofitInstance.api.getPokemonFlavorText(pokemonId)
                                    val englishFlavorText = flavorTextResponse.flavor_text_entries
                                        .filter { it.language.name == "en" }
                                        .map {
                                            it.flavor_text.replace(
                                                "\\n|\\f|\\t".toRegex(),
                                                " "
                                            )
                                        }
                                        .distinct()

                                    val pokemon = Pokemon(
                                        id = pokemonId,
                                        name = pokemonResult.name,
                                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png",
                                        types = types,
                                        flavor_text = englishFlavorText
                                    )

                                    // Insere no banco de dados
                                    val pokemonEntity = PokemonEntity(
                                        id = pokemonId,
                                        name = pokemon.name,
                                        imageUrl = pokemon.imageUrl,
                                        types = types,
                                        flavorText = englishFlavorText
                                    )
                                    pokemonDao.insertPokemon(listOf(pokemonEntity)) // Salva no banco

                                    pokemon
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
                } else {
                    // Carrega os Pokémon do banco de dados
                    val pokemonFromDb = cachedPokemon.map { it.toPokemon() }
                    pokemonList.addAll(pokemonFromDb)
                }
            }finally {
                isLoading.value = false
            }
        }
    }

    private fun PokemonEntity.toPokemon(): Pokemon {
        return Pokemon(
            id = this.id,
            name = this.name,
            imageUrl = this.imageUrl,
            types = this.types,
            flavor_text = this.flavorText
        )
    }

    // Define o Pokémon selecionado
    fun selectPokemon(pokemon: Pokemon) {
        selectedPokemon.value = pokemon
    }
}
