package com.example.justsit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.justsit.models.Database
import com.example.justsit.models.Prenotazione

class GestionePrenotazione(application: Application):AndroidViewModel(application) {
    private val db=Database.getInstance(application)
    private var _listPrenotazioni=MutableLiveData(listOf<Prenotazione>())
    val listPrenotazioni:LiveData<List<Prenotazione>>
    get()=_listPrenotazioni

    fun insertPrenotazione(prenotazione: Prenotazione){
        db.prenotazioneDao().insert(prenotazione)
    }
    fun confermaPrenotazione(prenotazione: Prenotazione){
        db.prenotazioneDao().confermaPrenotazione(prenotazione.ristorante, prenotazione.turno, prenotazione.tavolo, prenotazione.cliente)
    }
    fun deletePrenotazione(prenotazione: Prenotazione){
        db.prenotazioneDao().delete(prenotazione)
    }
    fun getPrenotazioniByUtente(username:String){
        val x = db.prenotazioneDao().getAllPrenotazioniByUsername(username)
        _listPrenotazioni.value = x
    }
    fun getPrenotazioniByRistorante(id:Int){
        val x = db.prenotazioneDao().getAllPrenotazioniByRistorante(id)
        _listPrenotazioni.value = x
    }
    fun getPrenotazioniConfermateByUtente(username:String){
        val x = db.prenotazioneDao().getPrenotazioniConfermateByUsername(username)
        _listPrenotazioni.value = x
    }
    fun getPrenotazioniNonConfermateByUtente(username:String){
        val x = db.prenotazioneDao().getPrenotazioniNonConfermateByUsername(username)
        _listPrenotazioni.value = x
    }
    fun getPrenotazioniPassateByUtente(username:String){
        val x = db.prenotazioneDao().getPrenotazioniPassateByUsername(username)
        _listPrenotazioni.value = x
    }
    fun getPrenotazioniConfermateByRistorante(id:Int){
        val x = db.prenotazioneDao().getPrenotazioniConfermateByRistorante(id)
        _listPrenotazioni.value = x
    }
    fun getPrenotazioniConfermateByTurno(id:Int, turno:Int){
        val x = db.prenotazioneDao().getPrenotazioniConfermateByTurno(id, turno)
        _listPrenotazioni.value = x
    }
    fun getPrenotazioniNonConfermateByRistorante(id:Int){
        val x = db.prenotazioneDao().getPrenotazioniNonConfermateByRistorante(id)
        _listPrenotazioni.value = x
    }
    fun getPrenotazioniPassateByRistorante(id:Int){
        val x = db.prenotazioneDao().getPrenotazioniPassateByRistorante(id)
        _listPrenotazioni.value = x
    }

}