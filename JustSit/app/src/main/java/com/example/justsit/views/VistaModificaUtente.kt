package com.example.justsit.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.justsit.databinding.ActivityVistaModificaUtenteBinding
import com.example.justsit.models.Utente
import com.example.justsit.viewmodels.GestoreUtente
import com.google.android.material.snackbar.Snackbar

class VistaModificaUtente : AppCompatActivity() {

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
        var utente =Utente("", "", "", "", "", "")
        val observer = Observer<Utente>{
            utente = it
            binding.utenteNomeUpdate.setText(it.nome )
            binding.utenteCognomeUpdate.setText(it.cognome)
            binding.utenteEmailUpdate.setText(it.email)
            binding.utenteTelefonoUpdate.setText(it.telefono)
            binding.utentePasswordUpdate.setText(it.password)
            binding.utenteConfermaPasswordUpdate.setText(it.password)
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
                    utenteModel.update(Utente(user, binding.utentePasswordUpdate.toString(), binding.utenteNomeUpdate.toString(), binding.utenteCognomeUpdate.toString(), binding.utenteEmailUpdate.toString(), binding.utenteTelefonoUpdate.toString()))
                finish()
            }
        }
        binding.utenteElimina.setOnClickListener{
             val snackbar = Snackbar.make(binding.root, "Sei sicuro di voler eliminare l'account?", Snackbar.LENGTH_LONG)
             snackbar.setAction("CONFERMA") {
                 utenteModel.delete(utente)
                 Toast.makeText(this, "Chiusura applicazione...", Toast.LENGTH_LONG).show()


                 this.finishAffinity()

             }
            snackbar.show()
        }
        }
    }


