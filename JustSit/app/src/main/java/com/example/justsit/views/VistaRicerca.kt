package com.example.justsit.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.justsit.R
import com.example.justsit.databinding.ActivityVistaRicercaBinding
import com.example.justsit.models.Ristorante
import com.example.justsit.viewmodels.GestoreRicerca
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class VistaRicerca : AppCompatActivity() {


    private lateinit var binding: ActivityVistaRicercaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaRicercaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = GestoreRicerca(this.application)
        val observer = Observer<List<Ristorante>>{
            for(ristorante in it) {
                val infl = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val rowView : View = infl.inflate(R.layout.ricerca_ristorante, null)
                rowView.findViewById<TextView>(R.id.ricerca_ristorante).text = ristorante.nome
                val posizione = StringBuilder()
                posizione.append(ristorante.cap).append(" ").append(ristorante.citta).append(" ").append(ristorante.indirizzo).append(", ").append(ristorante.civico)
                rowView.findViewById<TextView>(R.id.ricerca_posizione).text = posizione
                rowView.findViewById<ImageButton>(R.id.ristorante_dettagli).setOnClickListener{
                    val intent = Intent(this, VistaRistorante::class.java)
                    intent.putExtras(intent.extras!!)
                    startActivity(intent)
                }

            }
        }
        viewModel.ristoranteList.observe(this, observer)
        var date : Date
        if (intent.getStringExtra("data")==""){
           val calendar : Calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -1)
           date = calendar.time
        }
        else{
            val formatter = SimpleDateFormat("dd/mm/yyyy")
            date = formatter.parse(intent.getStringExtra("data")!!) as Date
        }


        viewModel.filteredSearch(intent.getSerializableExtra("ora_inizio") as Date, intent.getSerializableExtra("ora_fine") as Date, date, intent.getStringExtra("n_persone")!!.toInt(), intent.getStringExtra("citta") as String, intent.getStringExtra("tipologia") as String)
        binding.ricercaBack.setOnClickListener{
            finish()
        }


    }

}