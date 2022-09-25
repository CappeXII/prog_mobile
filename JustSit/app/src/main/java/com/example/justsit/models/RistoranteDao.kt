package com.example.justsit.models

import androidx.room.*
import java.util.*

@Dao
interface RistoranteDao {
    @Query("SELECT * FROM ristorante")
    suspend fun getAllRistoranti():List<Ristorante>
    @Query("SELECT *  FROM ristorante as r WHERE exists(select r.id_ristorante, turno.turno, tavolo.tavolo from ristorante inner join turno on ristorante.id_ristorante=turno.ristorante inner join tavolo on ristorante.id_ristorante=tavolo.ristorante where turno.orarioinizio>=:orarioinizio and turno.orariofine<=:orariofine and tavolo.npersone>=:npersone and ristorante.citta like :citta and ristorante.tipologia like :tipologia except select ristorante, turno, tavolo from prenotazione where data=:data)")
    suspend fun getRistorantiFree(orarioinizio:Long, orariofine:Long, data:Long, npersone:Int, citta:String, tipologia:String):List<Ristorante>
    @Query("SELECT * FROM ristorante where id_ristorante=:id")
    suspend fun getRistoranteById(id:Int):Ristorante
    @Query("SELECT * FROM ristorante WHERE username=:username ")
    suspend fun getRistoranteByUsername(username:String):Ristorante
    @Insert
    suspend fun insert(ristorante:Ristorante)
    @Update
    suspend fun update(ristorante: Ristorante)
    @Delete
    suspend fun delete(ristorante: Ristorante)
}