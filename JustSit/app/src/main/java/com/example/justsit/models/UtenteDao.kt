package com.example.justsit.models

import androidx.room.*

@Dao
interface UtenteDao {
    @Query("SELECT * FROM utente")
    fun getAllUtenti():List<Utente>
    @Query("SELECT * FROM utente WHERE username=:username")
    fun getUtenteByUsername(username:String):Utente
    @Insert
    fun insert(vararg utente: Utente)
    @Update
    fun update(utente: Utente)
    @Delete
    fun delete(utente: Utente)
}