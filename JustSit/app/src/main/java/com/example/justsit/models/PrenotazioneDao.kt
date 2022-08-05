package com.example.justsit.models

import androidx.room.*
import java.util.*

@Dao
interface PrenotazioneDao {
    @Query("SELECT * FROM prenotazione")
    fun getAllPrenotazioni():List<Prenotazione>
    @Query("SELECT * FROM prenotazione where cliente=:cliente")
    fun getAllPrenotazioniByUsername(cliente:String):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where ristorante=:ristorante")
    fun getAllPrenotazioniByRistorante(ristorante:Int):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where cliente=:cliente and data<date(CURRENT_DATE)")
    fun getPrenotazioniPassateByUsername(cliente:String):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where ristorante=:ristorante and data<date(CURRENT_DATE)")
    fun getPrenotazioniPassateByRistorante(ristorante:Int):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where cliente=:cliente and data>=date(CURRENT_DATE) and confermato=1")
    fun getPrenotazioniConfermateByUsername(cliente:String):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where ristorante=:ristorante and data<=date(CURRENT_DATE) and confermato=1")
    fun getPrenotazioniConfermateByRistorante(ristorante:Int):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where ristorante=:ristorante and turno=:turno and data<=date(CURRENT_DATE) and confermato=1")
    fun getPrenotazioniConfermateByTurno(ristorante:Int, turno:Int):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where cliente=:cliente and data>=date(CURRENT_DATE) and confermato=0")
    fun getPrenotazioniNonConfermateByUsername(cliente:String):List<Prenotazione>
    @Query("SELECT * FROM prenotazione where ristorante=:ristorante and data>=date(CURRENT_DATE) and confermato=1")
    fun getPrenotazioniNonConfermateByRistorante(ristorante:Int):List<Prenotazione>
    @Insert
    fun insert(vararg prenotazione:Prenotazione)
    @Query("UPDATE prenotazione SET confermato=1 where ristorante=:id and turno=:turno and tavolo=:tavolo and cliente=:cliente")
    fun confermaPrenotazione(id:Int, turno:Int, tavolo:Int, cliente:String)
    @Delete
    fun  delete(prenotazione: Prenotazione)

}