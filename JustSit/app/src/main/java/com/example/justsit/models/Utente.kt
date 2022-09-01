package com.example.justsit.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "utente")
data class Utente(
    @PrimaryKey val username:String,
    val password:String,
    val nome:String,
    val cognome:String,
    val email:String,
    val telefono:String,
)
