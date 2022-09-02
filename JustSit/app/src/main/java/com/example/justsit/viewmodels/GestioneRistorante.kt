package com.example.justsit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.justsit.models.Database
import com.example.justsit.models.Ristorante
import com.example.justsit.models.Tavolo
import com.example.justsit.models.Turno
import java.util.*

class GestioneRistorante(application: Application):AndroidViewModel(application) {
    private val db =Database.getInstance(application)
    private var _turno = MutableLiveData(Turno(0, 0, Date(), Date()))
    val turno : LiveData<Turno>
    get()=_turno
    private var _listTurno = MutableLiveData(listOf<Turno>())
    val listTurno : LiveData<List<Turno>>
        get() = _listTurno

    private var _listTavolo = MutableLiveData(listOf<Tavolo>())
    val listTavolo : LiveData<List<Tavolo>>
        get() = _listTavolo

    private var _ristorante = MutableLiveData(Ristorante(0, "", "", "", "", "", "", "", "", "", "", "", ""))
    val ristorante: LiveData<Ristorante>
        get()=_ristorante

    fun readRistorante(id:Int){
        val x = db.ristoranteDao().getRistoranteById(id)
        _ristorante.value = x
    }
    fun readTurni(id:Int){
        val x = db.turnoDao().getTurnoByRistorante(id)
        _listTurno.value = x
    }
    fun readTavoli(id:Int){
        val x = db.tavoloDao().getTavoloByRistorante(id)
        _listTavolo.value = x
    }
    fun insertTurno(turno: Turno){
        db.turnoDao().insert(turno)
    }
    fun insertTavolo(tavolo: Tavolo){
        db.tavoloDao().insert(tavolo)
    }
    fun updateTurno(turno: Turno){
        db.turnoDao().update(turno)
    }
    fun updateTavolo(tavolo: Tavolo){
        db.tavoloDao().update(tavolo)
    }
    fun updateRistorante(ristorante: Ristorante){
        db.ristoranteDao().update(ristorante)
    }
    fun deleteTurno(turno: Turno){
        db.turnoDao().delete(turno)
    }
    fun deleteTavolo(tavolo: Tavolo){
        db.tavoloDao().delete(tavolo)
    }
    fun deleteRistorante(ristorante: Ristorante){
        db.ristoranteDao().delete(ristorante)
    }
    fun getTurnoById(id: Int){
        val x =db.turnoDao().getTurnoById(id)
        _turno.value= x
    }
    fun readRistoranteByUsername(username:String){
        val x = db.ristoranteDao().getRistoranteByUsername(username)
        _ristorante.value = x
    }
}