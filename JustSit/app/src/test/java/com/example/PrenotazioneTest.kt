package com.example

import android.app.Application
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.justsit.models.*
import com.example.justsit.viewmodels.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import java.time.Instant
import java.util.*

class PrenotazioneTest {
    private val application = mock(Application::class.java)
    private val login = GestoreLogin(application)
    private val ristorante = GestioneRistorante(application)
    private val controllore = GestionePrenotazione(application)
    private val utente = GestoreUtente(application)
    private val ricerca = GestoreRicerca(application)
    private val prenotazione = Prenotazione("utente1", 1, 1, 1, false, Date.from(Instant.now()))
    @Before
    fun setUp(){
        login.insertUtente(Utente("utente1", "", "Utente", "1", "", ""))
        login.insertRistorante(Ristorante(1, "ristorante1", "", "Ristorante", "", "", "", "", "", "", "", "", ""))
        ristorante.insertTavolo(Tavolo(1, 1, 8))
        ristorante.insertTurno(Turno(1, 1, Date(0, 0, 0, 18, 30), Date(0,0,0,21,0)))
    }
    @After
    fun tearDown(){
        val utenteOb = Observer<List<Utente>>{
            for(utenti in it)
                utente.delete(utenti)
        }
        utente.utenteList.observe(application.getSystemService<LifecycleOwner>()!!, utenteOb)
        val ristoranteOb = Observer<List<Ristorante>>{
            for(ristoranti in it)
                ristorante.deleteRistorante(ristoranti)
        }
        ricerca.ristoranteList.observe(application.getSystemService<LifecycleOwner>()!!, ristoranteOb)
        utente.readAllUtenti()
        ricerca.getAllRistoranti()
    }
    @Test
    fun insert(){
        controllore.insertPrenotazione(prenotazione)
        val observer = Observer<List<Prenotazione>>{
            var check = false
            while(!check)
                for(prenotazione in it)
                    check = prenotazione.cliente =="utente1" && prenotazione.tavolo==1 && prenotazione.turno == 1 && prenotazione.ristorante == 1
            assert(check)
        }
        controllore.listPrenotazioni.observe(application.getSystemService<LifecycleOwner>()!!, observer)
        controllore.getPrenotazioniNonConfermateByUtente("utente1")
    }
    @Test
    fun delete(){
        controllore.deletePrenotazione(prenotazione)
        val observer = Observer<List<Prenotazione>>{
            var check = false
            while(!check)
                for(prenotazione in it)
                    check = prenotazione.cliente == "utente1" && prenotazione.ristorante==1 && prenotazione.tavolo == 1
                            && prenotazione.turno == 1
            assert(check)
        }
        controllore.listPrenotazioni.observe(application.getSystemService<LifecycleOwner>()!!, observer)
        controllore.getPrenotazioniNonConfermateByUtente("utente1")
    }
}