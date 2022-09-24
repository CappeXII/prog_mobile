package com.example.justsit.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PrenotazioneDao {
    @Query("SELECT * FROM prenotazione where cliente=:cliente and data<date(CURRENT_DATE)")
    suspend fun getPrenotazioniPassateByUsername(cliente:String):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where ristorante=:ristorante and data<date(CURRENT_DATE)")
    suspend fun getPrenotazioniPassateByRistorante(ristorante:Int):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where cliente=:cliente and data>=date(CURRENT_DATE) and confermato=1")
    suspend fun getPrenotazioniConfermateByUsername(cliente:String):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where ristorante=:ristorante and data<=date(CURRENT_DATE) and confermato=1")
    suspend fun getPrenotazioniConfermateByRistorante(ristorante:Int):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where ristorante=:ristorante and turno=:turno and data<=date(CURRENT_DATE) and confermato=1")
    suspend fun getPrenotazioniConfermateByTurno(ristorante:Int, turno:Int):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where cliente=:cliente and data>=date(CURRENT_DATE) and confermato=0")
    suspend fun getPrenotazioniNonConfermateByUsername(cliente:String):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where ristorante=:ristorante and data>=date(CURRENT_DATE) and confermato=1")
    suspend fun getPrenotazioniNonConfermateByRistorante(ristorante:Int):List<Prenotazione>
    @Insert
    suspend fun insert(vararg prenotazione:Prenotazione)
    @Query("UPDATE prenotazione SET confermato=1 where ristorante=:id and turno=:turno and tavolo=:tavolo and cliente=:cliente")
    suspend fun confermaPrenotazione(id:Int, turno:Int, tavolo:Int, cliente:String)
    @Delete
    suspend fun  delete(prenotazione: Prenotazione)

}