package com.example.justsit.models

import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.*

@Entity(tableName = "turno", primaryKeys = ["turno", "ristorante"], foreignKeys = arrayOf(ForeignKey(entity = Ristorante::class, parentColumns = arrayOf("id_ristorante"), childColumns = arrayOf("ristorante"), onDelete = ForeignKey.CASCADE)))
data class Turno(
    val turno:Int,
    val ristorante:Int,
    val orarioinizio:Long,
    val orariofine:Long
)
