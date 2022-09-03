package com.example.justsit.views

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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

class HomeUtente() : AppCompatActivity() {
    lateinit var user : String
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val binding : ActivityHomeUtenteBinding = ActivityHomeUtenteBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home_utente)
        val drawerLayout : DrawerLayout = binding.Drawerlayout
        val navView: NavigationView = binding.navView
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val utenteModel = GestoreUtente(this.application)
        val ristoranteModel = GestoreRicerca(this.application)
        val ristoranteObserver = Observer<List<Ristorante>> {
            val array : Array<String> = emptyArray()
            array[0]="tipologia"
            for (ristorante in it) {
                var check = true
                for (r in array)
                {
                    if(r == ristorante.tipologia)
                        check = false
                }
                if(check)
                    array[array.lastIndex+1] = ristorante.tipologia
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
        binding.navBtn.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.utenteSearch.setOnClickListener{
            val formatter : DateFormat = SimpleDateFormat("hh:mm")
            val inizio : Date
            val fine : Date
            if(binding.utenteOrarioInizioSearch.toString()=="")
                inizio = formatter.parse("00:00") as Date
            else
                inizio = formatter.parse(binding.utenteOrarioInizioSearch.toString()) as Date
            if(binding.utenteOrarioFineSearch.toString()=="")
                fine = formatter.parse("00:00") as Date
            else
                fine = formatter.parse(binding.utenteOrarioFineSearch.toString()) as Date
            val search = Intent(this, VistaRicerca::class.java)
            intent.putExtra("nome", binding.utenteRistoranteSearch.toString())
            intent.putExtra("data", binding.utenteDataSearch.toString())
            intent.putExtra("ora_inizio", inizio)
            intent.putExtra("ora_fine", fine)
            intent.putExtra("n_persone", binding.utentePersoneSearch.toString())
            intent.putExtra("citta", binding.utenteCittaSearch.toString())
            intent.putExtra("tipologia", binding.utenteTipologiaSearch.selectedItem.toString())
            intent.putExtra("username", user)
            startActivity(search)

            }
        val profilo : TextView = findViewById<TextView>(R.id.visualizza_profilo_utente)
        profilo.setOnClickListener{
            val intent = Intent(this, VistaModificaUtente::class.java)
            intent.putExtra("username", user)
            startActivity(intent)
        }
        val prenotazioni = findViewById<TextView>(R.id.gestisci_prenotazione_utente)
        prenotazioni.setOnClickListener{
            val intent = Intent(this, VistaPrenotazioneUtente::class.java)
            intent.putExtra("username", user)
            startActivity(intent)
        }
        val logout = findViewById<TextView>(R.id.logout_utente)
        logout.setOnClickListener{
            finish()
        }

        }

    }





