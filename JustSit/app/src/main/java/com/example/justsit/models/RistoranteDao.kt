package com.example.justsit.models

import androidx.room.*
import java.util.*

@Dao
interface RistoranteDao {
    @Query("SELECT * FROM ristorante")
    fun getAllRistoranti():List<Ristorante>
    @Query("SELECT *  FROM ristorante as r WHERE exists(select r.id_ristorante, turno.turno, tavolo.tavolo from ristorante inner join turno on ristorante.id_ristorante=turno.ristorante inner join tavolo on ristorante.id_ristorante=tavolo.ristorante where turno.orarioinizio<=:orarioinizio and turno.orariofine>=:orariofine and tavolo.npersone>=:npersone and ristorante.citta like :citta and ristorante.tipologia like :tipologia except select ristorante, turno, tavolo from prenotazione where data=:data)")
    fun getRistorantiFree(orarioinizio:Date, orariofine:Date, data:Date, npersone:Int, citta:String, tipologia:String):List<Ristorante>
    @Query("SELECT * FROM ristorante where id_ristorante=:id")
    fun getRistoranteById(id:Int):Ristorante
    @Insert
    fun insert(ristorante:Ristorante)
    @Update
    fun update(ristorante: Ristorante)
    @Delete
    fun delete(ristorante: Ristorante)
}