package com.example.justsit.models

import androidx.room.*
import java.util.*

@Dao
interface TavoloDao {
    @Query("SELECT * FROM tavolo WHERE ristorante=:ristorante")
    suspend fun getTavoloByRistorante(ristorante:Int):List<Tavolo>
    @Query("SELECT *  FROM tavolo as t WHERE exists(select ristorante.id_ristorante, turno.turno, tavolo.tavolo from ristorante inner join turno on ristorante.id_ristorante=turno.ristorante inner join tavolo on ristorante.id_ristorante=tavolo.ristorante where t.tavolo=tavolo.tavolo and t.ristorante=tavolo.ristorante and turno.orarioinizio>=:orarioinizio and turno.orariofine<=:orariofine and tavolo.npersone>=:npersone and ristorante.citta like :citta and ristorante.tipologia like :tipologia and ristorante.id_ristorante=:ristorante except select ristorante, turno, tavolo from prenotazione where data=:data)")
    suspend fun getTavoloFreeByRistorante(orarioinizio:Long, orariofine:Long, data:Long, npersone:Int, citta:String, tipologia:String, ristorante:Int):List<Tavolo>
    @Insert
    suspend fun insert(vararg tavolo:Tavolo)
    @Update
    suspend fun update(tavolo: Tavolo)
    @Delete
    suspend fun delete(tavolo: Tavolo)
}