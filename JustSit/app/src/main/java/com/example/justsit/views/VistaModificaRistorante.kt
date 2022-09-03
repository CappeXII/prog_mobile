package com.example.justsit.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.justsit.databinding.ActivityVistaModificaRistoranteBinding
import com.example.justsit.models.Ristorante
import com.example.justsit.viewmodels.GestioneRistorante
import com.google.android.material.snackbar.Snackbar

class VistaModificaRistorante : AppCompatActivity() {

    private lateinit var binding: ActivityVistaModificaRistoranteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaModificaRistoranteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.modificaRistoranteBack.setOnClickListener{
            finish()
        }
        val id = intent.getIntExtra("id",0)
        var user = ""
        var ristorante = Ristorante(0, "", "", "", "", "", "", "", "", "", "", "", "")
        val viewModel=GestioneRistorante(this.application)
        val observer = Observer<Ristorante>{
            binding.ristoranteNomeUpdate.setText(it.nome)
            binding.ristoranteDescrizioneUpdate.setText(it.descrizione)
            binding.ristoranteMenuUpdate.setText(it.menu)
            binding.ristoranteCapUpdate.setText(it.cap)
            binding.ristoranteCittaUpdate.setText(it.citta)
            binding.ristoranteIndirizzoUpdate.setText(it.indirizzo)
            binding.ristoranteCivicoUpdate.setText(it.civico)
            binding.ristoranteEmailUpdate.setText(it.email)
            binding.ristoranteTelefonoUpdate.setText(it.telefono)
            binding.ristorantePasswordUpdate.setText(it.password)
            binding.ristoranteConfermaUpdate.setText(it.password)
            binding.ristoranteTipologiaUpdate.setText(it.tipologia)
            user=it.username
            ristorante = it
        }
        viewModel.ristorante.observe(this, observer)
        viewModel.readRistorante(id)
        binding.ristoranteUpdateBtn.setOnClickListener{
            if(binding.ristoranteConfermaUpdate.toString() != binding.ristorantePasswordUpdate.toString())
                Snackbar.make(binding.root, "Errore nella conferma della password", Snackbar.LENGTH_SHORT).show()
            else{
                if( binding.ristorantePasswordUpdate.toString()==""|| binding.ristoranteNomeUpdate.toString()==""|| binding.ristoranteCittaUpdate.toString()=="" || binding.ristoranteIndirizzoUpdate.toString()=="")
                    Snackbar.make(binding.root, "Username, password, nome, citta' e indirizzo sono parametri obbligatori", Snackbar.LENGTH_LONG).show()
                else
                    viewModel.updateRistorante(Ristorante(id, user, binding.ristorantePasswordUpdate.toString(), binding.ristoranteNomeUpdate.toString(), binding.ristoranteDescrizioneUpdate.toString(), binding.ristoranteMenuUpdate.toString(), binding.ristoranteCapUpdate.toString(), binding.ristoranteCittaUpdate.toString(), binding.ristoranteIndirizzoUpdate.toString(), binding.ristoranteCivicoUpdate.toString(), binding.ristoranteEmailUpdate.toString(), binding.ristoranteTelefonoUpdate.toString(), binding.ristoranteTipologiaUpdate.toString()))
            }

        }
        binding.ristoranteDeleteBtn.setOnClickListener {
            val snackbar = Snackbar.make(binding.root, "Sei sicuro di voler cancellare l'account?", Snackbar.LENGTH_SHORT)
            snackbar.setAction("CONFERMA") {
                viewModel.deleteRistorante(ristorante)
                Toast.makeText(this, "Chiusura applicazione...", Toast.LENGTH_LONG).show()
                this.finishAffinity()
            }
            snackbar.show()
        }

    }



}