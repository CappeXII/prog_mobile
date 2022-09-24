package com.example.justsit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.justsit.models.Database
import com.example.justsit.models.Messaggistica
import com.example.justsit.models.Prenotazione
import kotlinx.coroutines.launch

class GestionePrenotazione(application: Application):AndroidViewModel(application) {
    private val db=Database.getInstance(application)
    private var _listPrenotazioni=MutableLiveData(listOf<Prenotazione>())
    val listPrenotazioni:LiveData<List<Prenotazione>>
    get()=_listPrenotazioni
    private var _listMessaggistica=MutableLiveData(listOf<Messaggistica>())
    val listMessaggistica:LiveData<List<Messaggistica>>
    get()=_listMessaggistica

    fun insertPrenotazione(prenotazione: Prenotazione){
        viewModelScope.launch {
            db.prenotazioneDao().insert(prenotazione)
        }
    }
    fun confermaPrenotazione(prenotazione: Prenotazione){
        viewModelScope.launch {
        db.prenotazioneDao().confermaPrenotazione(prenotazione.ristorante, prenotazione.turno, prenotazione.tavolo, prenotazione.cliente)
        }
    }
    fun deletePrenotazione(prenotazione: Prenotazione){
        viewModelScope.launch {
        db.prenotazioneDao().delete(prenotazione)
        }
    }

    fun getPrenotazioniConfermateByUtente(username:String){
        viewModelScope.launch {
        val x = db.prenotazioneDao().getPrenotazioniConfermateByUsername(username)
        _listPrenotazioni.value = x
        }
    }
    fun getPrenotazioniNonConfermateByUtente(username:String){
        viewModelScope.launch {
            val x = db.prenotazioneDao().getPrenotazioniNonConfermateByUsername(username)
            _listPrenotazioni.value = x
        }
    }
    fun getPrenotazioniPassateByUtente(username:String){
        viewModelScope.launch {
        val x = db.prenotazioneDao().getPrenotazioniPassateByUsername(username)
        _listPrenotazioni.value = x
        }
    }
    fun getPrenotazioniConfermateByRistorante(id:Int){
        viewModelScope.launch {
            val x = db.prenotazioneDao().getPrenotazioniConfermateByRistorante(id)
            _listPrenotazioni.value = x
        }
    }
    fun getPrenotazioniConfermateByTurno(id:Int, turno:Int){
        viewModelScope.launch {
            val x = db.prenotazioneDao().getPrenotazioniConfermateByTurno(id, turno)
            _listPrenotazioni.value = x
        }
    }
    fun getPrenotazioniNonConfermateByRistorante(id:Int){
        viewModelScope.launch {
            val x = db.prenotazioneDao().getPrenotazioniNonConfermateByRistorante(id)
            _listPrenotazioni.value = x
        }
    }
    fun getPrenotazioniPassateByRistorante(id:Int){
        viewModelScope.launch {
            val x = db.prenotazioneDao().getPrenotazioniPassateByRistorante(id)
            _listPrenotazioni.value = x
        }
    }
    fun getMessaggioByKey(cliente:String, tavolo:Int, turno:Int, ristorante:Int, mittente:String){
        viewModelScope.launch {
            val x = db.messaggisticaDao()
                .getMessaggiByPrenotazione(cliente, turno, tavolo, ristorante, mittente)
            _listMessaggistica.value = x
        }
    }
    fun insertMessaggio(messaggistica: Messaggistica){
        viewModelScope.launch {
            db.messaggisticaDao().insert(messaggistica)
        }
    }

}