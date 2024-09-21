package com.example.pokedexpwm

import android.app.Application
import androidx.room.Room
import com.example.pokedexpwm.DataBase.PokemonDatabase

class PokedexApplication : Application() {

    lateinit var database: PokemonDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        // Inicializa o banco de dados Room
        database = Room.databaseBuilder(
            applicationContext,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).build()
    }
}
