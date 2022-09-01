package com.example.justsit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.justsit.models.Database
import com.example.justsit.models.Utente

class GestoreUtente(application: Application):AndroidViewModel(application) {
    private val db = Database.getInstance(application)
    private val _utente=MutableLiveData(Utente("", "", "", "", "", "", ""))
    val utente:LiveData<Utente>
    get()=_utente
    private val _utenteList=MutableLiveData(listOf<Utente>())
    val utenteList : LiveData<List<Utente>>
    get()= _utenteList

    fun readUtente(username:String){
        _utente.value=db.utenteDao().getUtenteByUsername(username)
    }
    fun delete(utente: Utente){
        db.utenteDao().delete(utente)
    }
    fun update(utente: Utente){
        db.utenteDao().update(utente)
    }
    fun readAllUtenti(){
        val x = db.utenteDao().getAllUtenti()
        _utenteList.value = x
    }
}