package com.example.justsit.views

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.justsit.R
import com.example.justsit.databinding.ActivityVistaModificaUtenteBinding
import com.example.justsit.models.Utente
import com.example.justsit.viewmodels.GestoreUtente

class VistaModificaUtente : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityVistaModificaUtenteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaModificaUtenteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.modificaBack.setOnClickListener{
            finish()
        }
        val utenteModel = GestoreUtente(this.application)
        val user = intent.getStringExtra("username")
        var utente :Utente =Utente("", "", "", "", "", "")
        val observer = Observer<Utente>{
            utente = it
            binding.utenteNomeUpdate.text = it.nome as Editable
            binding.utenteCognomeUpdate.text = it.cognome as Editable
            binding.utenteEmailUpdate.text = it.email as Editable
            binding.utenteTelefonoUpdate.text = it.telefono as Editable
            binding.utentePasswordUpdate.text = it.password as Editable
            binding.utenteConfermaPasswordUpdate.text = it.password as Editable
        }
        utenteModel.utente.observe(this, observer)
        utenteModel.readUtente(user!!)
        binding.utenteModifica.setOnClickListener{
            if(binding.utenteConfermaPasswordUpdate.toString() != binding.utentePasswordUpdate.toString())
                Snackbar.make(binding.root, "Errore nella conferma della password", Snackbar.LENGTH_SHORT).show()
            else{
                if( binding.utentePasswordUpdate.toString()=="" || binding.utenteNomeUpdate.toString()=="" || binding.utenteCognomeUpdate.toString()=="")
                    Snackbar.make(binding.root, "Username, password, nome e cognome sono parametri obbligatori", Snackbar.LENGTH_LONG).show()
                else
                    utenteModel.update(Utente(user!!, binding.utentePasswordUpdate.toString(), binding.utenteNomeUpdate.toString(), binding.utenteCognomeUpdate.toString(), binding.utenteEmailUpdate.toString(), binding.utenteTelefonoUpdate.toString()))
                finish()
            }
        }
        binding.utenteElimina.setOnClickListener{
             val snackbar = Snackbar.make(binding.root, "Sei sicuro di voler eliminare l'account?", Snackbar.LENGTH_LONG)
             snackbar.setAction("CONFERMA", View.OnClickListener {
                 utenteModel.delete(utente)

             })
        }
        }
    }


