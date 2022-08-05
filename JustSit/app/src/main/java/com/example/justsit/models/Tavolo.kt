package com.example.justsit.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "tavolo", primaryKeys =["tavolo", "ristorante"], foreignKeys = arrayOf(
    ForeignKey(entity = Ristorante::class, parentColumns = arrayOf("id_ristorante"), childColumns = arrayOf("ristorante"), onDelete = ForeignKey.CASCADE)
) )
data class Tavolo(
    val tavolo:Int,
    val ristorante:Int,
    val npersone:Int
)
