package com.example.justsit.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.justsit.R
import com.example.justsit.databinding.ActivityHomeRistoranteBinding
import com.example.justsit.models.*
import com.example.justsit.viewmodels.GestionePrenotazione
import com.example.justsit.viewmodels.GestioneRistorante
import com.example.justsit.viewmodels.GestoreUtente

class HomeRistorante : AppCompatActivity() {

    private lateinit var binding: ActivityHomeRistoranteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeRistoranteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getStringExtra("username")!!
        var id = 0
        val viewModel = GestioneRistorante(this.application)
        val prenotazioneModel = GestionePrenotazione(this.application)
        val utenteModel = GestoreUtente(this.application)
        val observerRistorante = Observer<Ristorante>{
            id = it.id_ristorante
            findViewById<TextView>(R.id.username).text=it.nome
        }
        viewModel.ristorante.observe(this, observerRistorante)
        viewModel.readRistoranteByUsername(user)
        viewModel.ristorante.observe(this, observerRistorante)
        val observerTurno = Observer<List<Turno>>{
            for (turno in it){
                val infl = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val rowView : View = infl.inflate(R.layout.home_ristorante_turni, null)
                val stringa = StringBuilder("TURNO ").append(turno.turno).append(" ").append(turno.orarioinizio.toString()).append(" - ").append(turno.orariofine.toString())
                rowView.findViewById<TextView>(R.id.turno_title).text = stringa
                val observerPrenotazione = Observer<List<Prenotazione>>{
                    for(prenotazione in it){
                        val infla = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                        val deepRowView : View = infla.inflate(R.layout.home_ristorante_tavolo, null)
                        deepRowView.findViewById<TextView>(R.id.home_ristorante_tavolo_numero).text = StringBuilder("TAVOLO ").append(prenotazione.tavolo)
                        val utenteObserver = Observer<Utente>{
                            deepRowView.findViewById<TextView>(R.id.home_ristorante_tavolo_cliente).text = StringBuilder(it.cognome.uppercase()).append(" ").append(it.nome.uppercase())
                        }
                        utenteModel.utente.observe(this, utenteObserver)
                        val messaggioObserver = Observer<List<Messaggistica>>{
                            for(messaggio in it)
                                deepRowView.findViewById<TextView>(R.id.home_ristorante_tavolo_commento).text = messaggio.contenuto
                        }
                        prenotazioneModel.listMessaggistica.observe(this, messaggioObserver)
                        utenteModel.readUtente(prenotazione.cliente)
                        prenotazioneModel.getMessaggioByKey(prenotazione.cliente, prenotazione.tavolo, prenotazione.turno, prenotazione.ristorante, "utente")
                        rowView.findViewById<LinearLayout>(R.id.turno_content).addView(deepRowView)
                    }

                }
                prenotazioneModel.listPrenotazioni.observe(this, observerPrenotazione)
                prenotazioneModel.getPrenotazioniConfermateByTurno(id, turno.turno)
                binding.homeRistoranteContent.addView(rowView)
            }
        }
        viewModel.listTurno.observe(this, observerTurno)
        viewModel.readTurni(id)





        val profilo = findViewById<TextView>(R.id.visualizza_profilo_ristorante)
        profilo.setOnClickListener{
            val intent = Intent(this, VistaModificaRistorante::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        val tavoli_update = findViewById<TextView>(R.id.modifica_tavolo)
        tavoli_update.setOnClickListener{
            val intent = Intent(this, VistaModificaTavolo::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        val turni_update = findViewById<TextView>(R.id.modifica_turno)
        turni_update.setOnClickListener{
            val intent = Intent(this, VistaModificaTurno::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        val prenotazioni = findViewById<TextView>(R.id.gestisci_prenotazione_ristorante)
        prenotazioni.setOnClickListener{
            val intent = Intent(this, VistaPrenotazioneRistorante::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        val logout = findViewById<TextView>(R.id.login_ristorante)
        logout.setOnClickListener{
            finish()
        }
        }


}