package com.example.justsit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.justsit.models.Database
import com.example.justsit.models.Ristorante
import com.example.justsit.models.Utente

class GestoreLogin(application: Application): AndroidViewModel(application) {
    private val db:Database= Database.getInstance(application)

    fun insertUtente(vararg utente: Utente){
        db.utenteDao().insert(*utente)
    }
    fun insertRistorante( ristorante: Ristorante){
        var i = 0
        for( ristoranti in db.ristoranteDao().getAllRistoranti()){
            if (ristoranti.id_ristorante>i)
                i=ristoranti.id_ristorante
        }
        ristorante.id_ristorante=i+1
        db.ristoranteDao().insert(ristorante)

    }
    fun login(username:String, password:String):String{
        for(ristoranti in db.ristoranteDao().getAllRistoranti()){
            if((ristoranti.username == username) and (ristoranti.password == password))
                return "ristorante"
        }
        for(utenti in db.utenteDao().getAllUtenti()){
            if((utenti.username == username) and (utenti.password == password))
                return "utente"
        }
        return "errore"
    }



}