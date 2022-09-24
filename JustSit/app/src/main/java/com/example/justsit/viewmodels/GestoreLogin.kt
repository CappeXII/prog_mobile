package com.example.justsit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.justsit.models.Database
import com.example.justsit.models.Ristorante
import com.example.justsit.models.Utente
import kotlinx.coroutines.launch

class GestoreLogin(application: Application): AndroidViewModel(application) {
    private val db:Database= Database.getInstance(application)

    fun insertUtente(vararg utente: Utente){
        viewModelScope.launch {
            db.utenteDao().insert(*utente)
        }
    }
    fun insertRistorante( ristorante: Ristorante){
        viewModelScope.launch {
            var i = 0
            for( ristoranti in db.ristoranteDao().getAllRistoranti()){
                if (ristoranti.id_ristorante>i)
                    i=ristoranti.id_ristorante
            }
            ristorante.id_ristorante=i+1
            db.ristoranteDao().insert(ristorante)

        }
    }
    suspend fun login(username:String, password:String):String {
        var ret ="errore"
        for (ristoranti in db.ristoranteDao().getAllRistoranti()) {
                if ((ristoranti.username == username) and (ristoranti.password == password)) {
                    ret = "ristorante"
                    return ret
                }
            }
            for (utenti in db.utenteDao().getAllUtenti()) {
                if ((utenti.username == username) and (utenti.password == password)) {
                    ret = "utente"
                    return ret
                }
            }
            return ret
        }

    }