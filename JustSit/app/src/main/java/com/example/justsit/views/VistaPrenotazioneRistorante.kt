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
import com.example.justsit.databinding.ActivityVistaPrenotazioneRistoranteBinding
import com.example.justsit.models.Messaggistica
import com.example.justsit.models.Prenotazione
import com.example.justsit.models.Utente
import com.example.justsit.viewmodels.GestionePrenotazione
import com.example.justsit.viewmodels.GestoreUtente

class VistaPrenotazioneRistorante : AppCompatActivity() {

    private lateinit var binding: ActivityVistaPrenotazioneRistoranteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaPrenotazioneRistoranteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("id", 0)
        val viewModel = GestionePrenotazione(this.application)
        val utenteModel = GestoreUtente(this.application)
        binding.vistaPrenotazioneRistoranteBack.setOnClickListener{
            finish()
        }
        binding.prenotazioneRistoranteConfermate.isEnabled=false
        val observer=Observer<List<Prenotazione>>{
            binding.prenotazioneRistoranteContent.removeAllViews()
            for(prenotazione in it){
                val infl=getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val rowView : View = infl.inflate(R.layout.prenotazione_ristorante, null)
                rowView.findViewById<TextView>(R.id.prenotazione_tavolo).text = StringBuilder("TAVOLO ").append(prenotazione.tavolo)
                val utenteObserver = Observer<Utente>{
                    rowView.findViewById<TextView>(R.id.prenotazione_cliente).text = StringBuilder(it.cognome.uppercase()).append(" ").append(it.nome.uppercase())
                }
                utenteModel.utente.observe(this, utenteObserver)
                utenteModel.readUtente(prenotazione.cliente)
                val messaggioObserver = Observer<List<Messaggistica>>{
                    for(messaggio in it) {
                        rowView.findViewById<TextView>(R.id.prenotazione_commento).text = messaggio.contenuto
                    }
                }
                viewModel.listMessaggistica.observe(this, messaggioObserver)
                viewModel.getMessaggioByKey(prenotazione.cliente, prenotazione.tavolo, prenotazione.turno, prenotazione.ristorante, "utente")
                rowView.findViewById<Button>(R.id.prenotazione_delete).setOnClickListener{
                    viewModel.deletePrenotazione(prenotazione)
                }
                if(!prenotazione.confermato)
                    rowView.findViewById<Button>(R.id.prenotazione_accept).visibility=View.GONE
                else
                    rowView.findViewById<Button>(R.id.prenotazione_accept).setOnClickListener{
                        viewModel.confermaPrenotazione(prenotazione)
                    }
                binding.prenotazioneRistoranteContent.addView(rowView)
            }

            }
        viewModel.listPrenotazioni.observe(this, observer)
        binding.prenotazioneRistoranteConfermate.setOnClickListener{
            binding.prenotazioneRistoranteConfermate.isEnabled=false
            binding.prenotazioneRistoranteRichieste.isEnabled=true
            binding.prenotazioneRistorantePassate.isEnabled=true
            viewModel.getPrenotazioniConfermateByRistorante(id)
        }
        binding.prenotazioneRistoranteRichieste.setOnClickListener{
            binding.prenotazioneRistoranteConfermate.isEnabled=true
            binding.prenotazioneRistoranteRichieste.isEnabled=false
            binding.prenotazioneRistorantePassate.isEnabled=true
            viewModel.getPrenotazioniNonConfermateByRistorante(id)
        }
        binding.prenotazioneRistorantePassate.setOnClickListener{
            binding.prenotazioneRistoranteConfermate.isEnabled=true
            binding.prenotazioneRistoranteRichieste.isEnabled=true
            binding.prenotazioneRistorantePassate.isEnabled=false
            viewModel.getPrenotazioniPassateByRistorante(id)
        }

    }
}