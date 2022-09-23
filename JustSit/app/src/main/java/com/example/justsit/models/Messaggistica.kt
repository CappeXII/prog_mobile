package com.example.justsit.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName ="messaggistica", primaryKeys = ["cliente", "tavolo", "turno", "ristorante"], foreignKeys = arrayOf(
    ForeignKey(entity = Ristorante::class, parentColumns = arrayOf("id_ristorante"), childColumns = arrayOf("ristorante"), onDelete = ForeignKey.CASCADE), ForeignKey(entity = Utente::class, parentColumns = arrayOf("username"), childColumns = arrayOf("cliente"), onDelete = ForeignKey.CASCADE))
)

data class Messaggistica(
    val cliente:String,
    val tavolo:Int,
    val turno:Int,
    val ristorante: Int,
    val mittente:String,
    val contenuto:String
)
