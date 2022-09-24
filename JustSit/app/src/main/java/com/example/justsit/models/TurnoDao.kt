package com.example.justsit.models

import androidx.room.*

@Dao
interface TurnoDao {
    @Query("SELECT * FROM turno WHERE ristorante=:ristorante")
    suspend fun getTurnoByRistorante(ristorante:Int):List<Turno>
    @Query("SELECT * FROM turno WHERE turno=:id")
    suspend fun getTurnoById(id:Int):Turno
    @Insert
    suspend fun insert(vararg turno: Turno)
    @Update
    suspend fun update(turno: Turno)
    @Delete
    suspend fun delete(turno: Turno)
}