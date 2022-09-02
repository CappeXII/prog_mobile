package com.example.justsit.models

import androidx.room.*

@Dao
interface TurnoDao {
    @Query("SELECT * FROM turno WHERE ristorante=:ristorante")
    fun getTurnoByRistorante(ristorante:Int):List<Turno>
    @Query("SELECT * FROM turno WHERE turno=:id")
    fun getTurnoById(id:Int):Turno
    @Insert
    fun insert(vararg turno: Turno)
    @Update
    fun update(turno: Turno)
    @Delete
    fun delete(turno: Turno)
}