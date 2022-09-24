package com.example.justsit.models

import androidx.room.*

@Dao
interface UtenteDao {
    @Query("SELECT * FROM utente")
    suspend fun getAllUtenti():List<Utente>
    @Query("SELECT * FROM utente WHERE username=:username")
    suspend fun getUtenteByUsername(username:String):Utente
    @Insert
    suspend fun insert(vararg utente: Utente)
    @Update
    suspend fun update(utente: Utente)
    @Delete
    suspend fun delete(utente: Utente)
}