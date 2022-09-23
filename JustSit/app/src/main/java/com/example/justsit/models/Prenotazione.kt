package com.example.justsit.models

import androidx.room.Entity
import androidx.room.ForeignKey

//mancano foreign key
@Entity(tableName ="prenotazione", primaryKeys = ["cliente", "tavolo", "turno", "ristorante"], foreignKeys = [ForeignKey(entity = Ristorante::class, parentColumns = arrayOf("id_ristorante"), childColumns = arrayOf("ristorante"), onDelete = ForeignKey.CASCADE), ForeignKey(entity = Utente::class, parentColumns = arrayOf("username"), childColumns = arrayOf("cliente"), onDelete = ForeignKey.CASCADE)])
data class Prenotazione(
    val cliente:String,
    val tavolo:Int,
    val turno:Int,
    val ristorante:Int,
    val confermato:Boolean,
    val data:Long
)
