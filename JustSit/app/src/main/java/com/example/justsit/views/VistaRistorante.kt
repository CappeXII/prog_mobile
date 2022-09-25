package com.example.justsit.views

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.justsit.R
import com.example.justsit.databinding.ActivityVistaRistoranteBinding
import com.example.justsit.models.*
import com.example.justsit.viewmodels.GestionePrenotazione
import com.example.justsit.viewmodels.GestioneRistorante
import com.example.justsit.viewmodels.GestoreRicerca
import java.text.SimpleDateFormat
import java.util.*

class VistaRistorante : AppCompatActivity() {


    private lateinit var binding: ActivityVistaRistoranteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaRistoranteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getStringExtra("username")!!
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val ristorante = intent.getIntExtra("ristorante", 0)
        val viewModel = GestoreRicerca(this.application)
        val viewRistorante = GestioneRistorante(this.application)
        binding.ristoranteModel = viewRistorante

        val viewPrenotazione = GestionePrenotazione(this.application)
        val turniArray= emptyList<String>().toMutableList()
        binding.ricercaPrenotazioneBack.setOnClickListener{
            finish()
        }
        val ristObserver = Observer<Ristorante>{
            binding.ricercaNomeRistorante.text = it.nome
            binding.ricercaEmailRistorante.text = it.email
            binding.ricercaTipologiaRistorante.text = it.tipologia
            binding.ricercaMenuRistorante.text= it.menu
            binding.ricercaTelefonoRistorante.text = it.telefono
            binding.RicercaLocalitRistorante.text = it.cap+" "+it.citta+" "+it.indirizzo+" "+it.civico
            binding.ricercaData.text = if (intent.getStringExtra("data") == "") formatter.format(Calendar.getInstance().time) else formatter.parse(intent.getStringExtra("data")!!).toString()
        }
        viewRistorante.ristorante.observe(this, ristObserver)
        viewRistorante.readRistorante(ristorante)
        val observer = Observer<List<Tavolo>>{
            for(tavolo in it){
                val infl = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val rowView :View = infl.inflate(R.layout.ricerca_tavolo, null)
                val nome_tavolo = StringBuilder()
                nome_tavolo.append("Tavolo ").append(tavolo.tavolo)
                val persone_tavolo = StringBuilder()
                persone_tavolo.append("Numero posti: ").append(tavolo.npersone)
                rowView.findViewById<TextView>(R.id.ricerca_tavolo_nome).text = nome_tavolo
                rowView.findViewById<TextView>(R.id.ricerca_tavolo_numero).text = persone_tavolo
                rowView.findViewById<ImageButton>(R.id.ricerca_tavolo_prenota).setOnClickListener{
                    val date : Date
                    date = if (intent.getStringExtra("data")==""){
                        val calendar : Calendar = Calendar.getInstance()
                        calendar.add(Calendar.DAY_OF_YEAR, -1)
                        calendar.time
                    } else {
                        val formatter = SimpleDateFormat("dd/mm/yyyy")
                        formatter.parse(intent.getStringExtra("data")!!)!!
                    }
                    val builder : AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Prenotazione tavolo: inserire un messaggio per il ristorante (opzionale)")
                    val input = EditText(this)
                    input.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                    builder.setView(input)
                    builder.setPositiveButton("PRENOTA" ){ dialog, which ->
                        viewPrenotazione.insertPrenotazione(Prenotazione(user, tavolo.tavolo, binding.ricercaListaTurni.selectedItem.toString().substring(6).toInt(), ristorante, false, date.time))
                        viewPrenotazione.insertMessaggio(Messaggistica(user, tavolo.tavolo, binding.ricercaListaTurni.selectedItem.toString().substring(6).toInt(), ristorante, "utente", input.text.toString()))

                    }
                    builder.show()
                    }
                binding.dettagliTurniContent.addView(rowView)
            }

        }
        viewModel.tavoloList.observe(this, observer)
        val turnoObserve = Observer<List<Turno>>{
            for(turno in it) {
                val stringa = StringBuilder()
                stringa.append("Turno ").append(turno.turno)
                turniArray.add(stringa.toString())
            }
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, turniArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.ricercaListaTurni.adapter = adapter
        }
        viewRistorante.listTurno.observe(this, turnoObserve)
        viewRistorante.getTurnoById(ristorante)
        binding.ricercaListaTurni.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val date : Date
                if (intent.getStringExtra("data") == ""){
                    val calendar : Calendar = Calendar.getInstance()
                    date = calendar.time
                }
                else{

                    date = formatter.parse(intent.getStringExtra("data")!!) as Date
                }
                val tipologia= if(intent.getStringExtra("tipologia") == "tipologia") "" else intent.getStringExtra("tipologia")!!
                val npersone = if(intent.getStringExtra("n_persone") == "") 0 else intent.getStringExtra("n_persone").toString().toInt()
                viewModel.tavoloFilteredSearch(intent.getSerializableExtra("ora_inizio") as Date, intent.getSerializableExtra("ora_fine") as Date, date, npersone, intent.getStringExtra("citta")!!, tipologia, ristorante)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


    }


}