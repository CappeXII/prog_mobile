package com.example.justsit.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessaggisticaDao {
    @Query("SELECT * FROM messaggistica where cliente=:cliente and turno=:turno and tavolo=:tavolo and ristorante=:ristorante and mittente=:mittente")
    suspend fun getMessaggiByPrenotazione(cliente:String, turno:Int, tavolo:Int, ristorante:Int, mittente:String):List<Messaggistica>
    @Insert
    suspend fun insert(vararg messaggi:Messaggistica)
    @Delete
    suspend fun delete(messaggi:Messaggistica)
}