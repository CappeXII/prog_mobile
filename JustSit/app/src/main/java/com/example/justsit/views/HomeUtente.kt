package com.example.justsit.views

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.example.justsit.R
import com.example.justsit.databinding.ActivityHomeUtenteBinding
import com.example.justsit.models.Ristorante
import com.example.justsit.models.Utente
import com.example.justsit.viewmodels.GestoreRicerca
import com.example.justsit.viewmodels.GestoreUtente
import com.google.android.material.navigation.NavigationView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeUtente : AppCompatActivity() {
    private lateinit var user : String
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityHomeUtenteBinding = ActivityHomeUtenteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val drawerLayout : DrawerLayout = binding.Drawerlayout
        val navView: NavigationView = binding.navViewUtente
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val utenteModel = GestoreUtente(this.application)
        val ristoranteModel = GestoreRicerca(this.application)
        val ristoranteObserver = Observer<List<Ristorante>> {
            val array = emptyList<String>().toMutableList()
            array.add("tipologia")
            for (ristorante in it) {
                var check = true
                for (r in array)
                {
                    if(r == ristorante.tipologia)
                        check = false
                }
                if(check)
                    array.add(ristorante.tipologia)
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
            binding.utenteTipologiaSearch.adapter=adapter

        }
        ristoranteModel.ristoranteList.observe(this, ristoranteObserver)
        ristoranteModel.getAllRistoranti()
        val utenteObserver = Observer<Utente>{
            user = it.username
            val header = navView.getHeaderView(0)
            val username = header.findViewById<TextView>(R.id.username)
            username.text = user
        }
        utenteModel.utente.observe(this, utenteObserver)
        utenteModel.readUtente(intent.getStringExtra("username")!!)
        binding.utenteSearch.setOnClickListener{
            val formatter : DateFormat = SimpleDateFormat("hh:mm")
            val inizio : Date
            val fine : Date
            if(binding.utenteOrarioInizioSearch.text.toString()=="")
                inizio = formatter.parse("00:00") as Date
            else
                inizio = formatter.parse(binding.utenteOrarioInizioSearch.toString()) as Date
            if(binding.utenteOrarioFineSearch.text.toString()=="")
                fine = formatter.parse("23:59") as Date
            else
                fine = formatter.parse(binding.utenteOrarioFineSearch.toString()) as Date
            val search = Intent(this, VistaRicerca::class.java)
            search.putExtra("nome", binding.utenteRistoranteSearch.text.toString())
            search.putExtra("data", binding.utenteDataSearch.text.toString())
            search.putExtra("ora_inizio", inizio)
            search.putExtra("ora_fine", fine)
            search.putExtra("n_persone", binding.utentePersoneSearch.text.toString())
            search.putExtra("citta", binding.utenteCittaSearch.text.toString())
            search.putExtra("tipologia", binding.utenteTipologiaSearch.selectedItem.toString())
            search.putExtra("username", user)
            startActivity(search)

        }

        binding.navViewUtente.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.visualizza_profilo_utente ->{
                    val intent = Intent(this, VistaModificaUtente::class.java)
                    intent.putExtra("username", user)
                    startActivity(intent)
                    true
                }
                R.id.gestisci_prenotazione_utente ->{
                    val intent = Intent(this, VistaPrenotazioneUtente::class.java)
                    intent.putExtra("username", user)
                    startActivity(intent)
                    true
                }
                R.id.logout_utente ->{
                    finish()
                    true
                }
                else -> false
            }
        }


    }

}





