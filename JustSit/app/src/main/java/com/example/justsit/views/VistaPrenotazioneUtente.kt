package com.example.justsit.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.justsit.R
import com.example.justsit.databinding.ActivityVistaPrenotazioneUtenteBinding
import com.example.justsit.models.Messaggistica
import com.example.justsit.models.Prenotazione
import com.example.justsit.models.Ristorante
import com.example.justsit.models.Turno
import com.example.justsit.viewmodels.GestionePrenotazione
import com.example.justsit.viewmodels.GestioneRistorante

class VistaPrenotazioneUtente : AppCompatActivity() {
    private lateinit var binding: ActivityVistaPrenotazioneUtenteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaPrenotazioneUtenteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.utentePrenotazioneBack.setOnClickListener{
            finish()
        }
        val viewModel = GestionePrenotazione(this.application)
        val ristoranteModel = GestioneRistorante(this.application)
        val user = intent.getStringExtra("username")!!
        val observer = Observer<List<Prenotazione>>{
            binding.utentePrenotazioneContent.removeAllViews()

            for(prenotazione in it){
                val infl = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val rowView : View = infl.inflate(R.layout.utente_prenotazione, binding.root)
                rowView.findViewById<TextView>(R.id.utente_prenotazione_data).text=prenotazione.data.toString()
                val tavolo =StringBuilder()
                tavolo.append("Tavolo ").append(prenotazione.tavolo.toString())
                rowView.findViewById<TextView>(R.id.utente_prenotazione_tavolo).text= tavolo
                val ristoranteObserver = Observer<Ristorante> {
                    rowView.findViewById<TextView>(R.id.prenotazione_ristorante).text=it.nome
                }
                val turnoObserver = Observer<Turno>{
                    val orario = StringBuilder()
                    orario.append(it.orarioinizio.toString()).append(" - ").append(it.orariofine.toString())
                    rowView.findViewById<TextView>(R.id.utente_prenotazione_orario).text= orario
                }
                val commentoObserver = Observer<List<Messaggistica>>{
                    for(messaggio in it) {
                        rowView.findViewById<TextView>(R.id.utente_prenotazione_commento).text =
                            messaggio.contenuto
                    }
                }

                ristoranteModel.ristorante.observe(this, ristoranteObserver)
                ristoranteModel.turno.observe(this, turnoObserver)
                viewModel.listMessaggistica.observe(this, commentoObserver)
                ristoranteModel.readRistorante(prenotazione.ristorante)
                ristoranteModel.readTurni(prenotazione.turno)
                viewModel.getMessaggioByKey(prenotazione.cliente, prenotazione.tavolo, prenotazione.turno, prenotazione.ristorante, "ristorante")
                rowView.findViewById<Button>(R.id.utente_prenotazione_delete_btn).setOnClickListener{
                    viewModel.deletePrenotazione(prenotazione)
                }
                binding.utentePrenotazioneContent.addView(rowView)
            }

        }
        viewModel.listPrenotazioni.observe(this, observer)
        binding.utentePrenotazioneConfermate.isEnabled = false
        viewModel.getPrenotazioniConfermateByUtente(user)
        binding.utentePrenotazioneConfermate.setOnClickListener{
            viewModel.getPrenotazioniConfermateByUtente(user)
            binding.utentePrenotazioneConfermate.isEnabled=false
            binding.utentePrenotazionePassate.isEnabled=true
            binding.utentePrenotazioneRichieste.isEnabled=true
        }
        binding.utentePrenotazioneRichieste.setOnClickListener{
            viewModel.getPrenotazioniNonConfermateByUtente(user)
            binding.utentePrenotazioneConfermate.isEnabled=true
            binding.utentePrenotazionePassate.isEnabled=true
            binding.utentePrenotazioneRichieste.isEnabled=false
        }
        binding.utentePrenotazionePassate.setOnClickListener{
            viewModel.getPrenotazioniPassateByUtente(user)
            binding.utentePrenotazioneConfermate.isEnabled=true
            binding.utentePrenotazionePassate.isEnabled=false
            binding.utentePrenotazioneRichieste.isEnabled=true
        }

    }


}