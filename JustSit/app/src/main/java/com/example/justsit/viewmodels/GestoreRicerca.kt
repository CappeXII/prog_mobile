package com.example.justsit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.justsit.models.Database
import com.example.justsit.models.Ristorante
import com.example.justsit.models.Tavolo
import kotlinx.coroutines.launch
import java.util.*

class GestoreRicerca(application: Application):AndroidViewModel(application) {
    private val db = Database.getInstance(application)
    private val _ristoranteList = MutableLiveData(listOf<Ristorante>())
    val ristoranteList : LiveData<List<Ristorante>>
    get()= _ristoranteList
    private val _tavoloList = MutableLiveData(listOf<Tavolo>())
    val tavoloList : LiveData<List<Tavolo>>
    get() = _tavoloList
    fun getAllRistoranti(){
        viewModelScope.launch {
            val x = db.ristoranteDao().getAllRistoranti()
            _ristoranteList.value = x
        }
    }
    fun filteredSearch(orarioinizio: Date, orariofine:Date, data:Date, npersone:Int, citta:String, tipologia:String){
        viewModelScope.launch {
            val orarioInizio:Date
            val orarioFine:Date
            val Data:Date
            val Citta:String
            val Tipologia:String
            if(orarioinizio == Date())
                orarioInizio = Date()
            else
                orarioInizio=orarioinizio
            if(orariofine== Date())
                orarioFine=Date(0, 0, 0, 23, 59)
            else
                orarioFine=orariofine
            if(data== Date())
                Data=Date(2030, 12, 31)
            else
                Data=data
            if(citta=="")
                Citta=""
            else
                Citta=citta
            if(tipologia=="")
                Tipologia = ""
            else
                Tipologia=tipologia
            val x = db.ristoranteDao().getRistorantiFree(orarioInizio.time, orarioFine.time, Data.time, npersone, Citta, Tipologia )
            _ristoranteList.value = x

        }
    }
    fun tavoloFilteredSearch(orarioinizio: Date, orariofine: Date, data: Date, npersone: Int, citta: String, tipologia: String, ristorante: Int){
        viewModelScope.launch {
            val orarioInizio:Date
            val orarioFine:Date
            val Data:Date
            val Citta:String
            val Tipologia:String
            if(orarioinizio == Date())
                orarioInizio = Date()
            else
                orarioInizio=orarioinizio
            if(orariofine==Date())
                orarioFine=Date(0, 0, 0, 23, 59)
            else
                orarioFine=orariofine
            if(data==Date())
                Data=Date(2030, 12, 31)
            else
                Data=data
            if(citta=="")
                Citta=""
            else
                Citta=citta
            if(tipologia=="")
                Tipologia=""
            else
                Tipologia=tipologia
            val x = db.tavoloDao().getTavoloFreeByRistorante(orarioInizio.time, orarioFine.time, Data.time, npersone, Citta, Tipologia, ristorante)
            _tavoloList.value=x
        }
    }
}