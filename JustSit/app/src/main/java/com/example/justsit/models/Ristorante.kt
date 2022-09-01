package com.example.justsit.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ristorante")
data class Ristorante(
    @PrimaryKey var id_ristorante:Int,
    val username:String,
    val password: String,
    val nome:String,
    val descrizione:String,
    val menu:String,
    val cap:String,
    val citta:String,
    val indirizzo:String,
    val civico:String,
    val email:String,
    val telefono:String,
    val tipologia:String,
)
