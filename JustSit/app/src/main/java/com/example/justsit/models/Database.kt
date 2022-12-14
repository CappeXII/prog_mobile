package com.example.justsit.models

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.*;

@androidx.room.Database(entities = [Messaggistica::class, Prenotazione::class, Ristorante::class, Tavolo::class, Turno::class,Utente::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun messaggisticaDao(): MessaggisticaDao
    abstract fun prenotazioneDao(): PrenotazioneDao
    abstract fun ristoranteDao():RistoranteDao
    abstract fun tavoloDao(): TavoloDao
    abstract fun turnoDao():TurnoDao
    abstract fun utenteDao():UtenteDao

    companion object{
        @Volatile
        private var INSTANCE: Database? =null
        fun getInstance(context: Context): Database{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java, "database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE= it }
            }
        }
    }
}