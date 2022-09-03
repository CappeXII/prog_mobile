package com.example.justsit.models

import androidx.room.ForeignKey
import androidx.room.Entity
import java.util.*
//mancano foreign key
@Entity(tableName ="prenotazione", primaryKeys = ["cliente", "tavolo", "turno", "ristorante"], foreignKeys = arrayOf(ForeignKey(entity = Ristorante::class, parentColumns = arrayOf("id_ristorante"), childColumns = arrayOf("ristorante"), onDelete = ForeignKey.CASCADE), ForeignKey(entity = Tavolo::class, parentColumns = arrayOf("tavolo"), childColumns = arrayOf("tavolo"), onDelete = ForeignKey.CASCADE), ForeignKey(entity = Utente::class, parentColumns = arrayOf("username"), childColumns = arrayOf("cliente"), onDelete = ForeignKey.CASCADE), ForeignKey(entity = Turno::class, parentColumns = arrayOf("turno"), childColumns = arrayOf("turno"), onDelete = ForeignKey.CASCADE)))
data class Prenotazione(
    val cliente:String,
    val tavolo:Int,
    val turno:Int,
    val ristorante:Int,
    val confermato:Boolean,
    val data:Date
)
