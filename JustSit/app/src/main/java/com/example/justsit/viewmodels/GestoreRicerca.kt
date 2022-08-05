package com.example.justsit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.justsit.models.Database
import com.example.justsit.models.Ristorante
import java.util.*

class GestoreRicerca(application: Application):AndroidViewModel(application) {
    private val db = Database.getInstance(application)
    private var _ristoranteList = MutableLiveData(listOf<Ristorante>())
    val ristoranteList : LiveData<List<Ristorante>>
    get()= _ristoranteList

    fun getAllRistoranti(){
        val x = db.ristoranteDao().getAllRistoranti()
        _ristoranteList.value = x
    }
    fun filteredSearch(orarioinizio: Date, orariofine:Date, data:Date, npersone:Int, citta:String, tipologia:String){
        var orarioInizio:Date
        var orarioFine:Date
        var Data:Date
        var Citta:String
        var Tipologia:String
        if(orarioinizio == null)
            orarioInizio = Date(0, 0, 0, 0, 0)
        else
            orarioInizio=orarioinizio
        if(orariofine==null)
            orarioFine=Date(0, 0, 0, 23, 59)
        else
            orarioFine=orariofine
        if(data==null)
            Data=Date(2030, 12, 31)
        else
            Data=data
        if(citta==null)
            Citta=""
        else
            Citta=citta
        if(tipologia==null)
            Tipologia=""
        else
            Tipologia=tipologia
        val x = db.ristoranteDao().getRistorantiFree(orarioInizio, orarioFine, Data, npersone, Citta, Tipologia )
            _ristoranteList.value = x

    }
}