package com.example.justsit.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.justsit.R
import com.example.justsit.databinding.*
import com.example.justsit.models.Ristorante
import com.example.justsit.models.Utente
import com.example.justsit.viewmodels.GestoreLogin
import com.example.justsit.viewmodels.GestoreRicerca
import com.example.justsit.viewmodels.GestoreUtente
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginScreen.isEnabled = false
        val frag = LoginFragment()
        val fragManager = supportFragmentManager
        val fragTransaction = fragManager.beginTransaction()
        fragTransaction.add(R.id.fragmentContainerView, frag)
        fragTransaction.commit()
        binding.registratiScreen.setOnClickListener{
            binding.loginScreen.isEnabled = true
            val loginTransaction = fragManager.beginTransaction()
            loginTransaction.replace(R.id.fragmentContainerView, RegistrazioneFragment())
            loginTransaction.commit()
            binding.registratiScreen.isEnabled = false
            }
        binding.loginScreen.setOnClickListener {
            binding.registratiScreen.isEnabled = true
            val registratiTransaction = fragManager.beginTransaction()
            registratiTransaction.replace(R.id.fragmentContainerView, LoginFragment())
            registratiTransaction.commit()
            binding.loginScreen.isEnabled= false
        }
        }
    }

class LoginFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : LoginFragmentBinding = LoginFragmentBinding.inflate(inflater, container, false)
        val viewModel = GestoreLogin(this.requireActivity().application)
        binding.loginButton.setOnClickListener{
            when( viewModel.login(binding.usernameText.text.toString(), binding.passwordText.text.toString())){
                "utente" -> {

                    val intent = Intent(this.context, HomeUtente::class.java)

                    startActivity(intent)
                }
                "ristorante" ->{
                    val intent = Intent(this.context, HomeRistorante::class.java)
                    intent.putExtra("username", binding.usernameText.toString())
                    startActivity(intent)
                }
                "errore" -> Snackbar.make(this.requireView(), "Errore nelle credenziali", Snackbar.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}

class RegistrazioneFragment:Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: RegistrazioneFragmentBinding = RegistrazioneFragmentBinding.inflate(inflater, container, false)
        binding.utenteScreen.isEnabled = false
        val manager = this.childFragmentManager.beginTransaction()
        manager.add(R.id.fragmentContainerViewRegistrazione, RegistrazioneUtenteFragment())
        manager.commit()
        binding.utenteScreen.setOnClickListener{
            binding.utenteScreen.isEnabled = false
            val utenteManager = this.childFragmentManager.beginTransaction()
            utenteManager.replace(R.id.fragmentContainerViewRegistrazione, RegistrazioneUtenteFragment())
            utenteManager.commit()
            binding.ristoranteScreen.isEnabled = true
        }
        binding.ristoranteScreen.setOnClickListener{
            binding.ristoranteScreen.isEnabled = false
            val ristoranteManager = this.childFragmentManager.beginTransaction()
            ristoranteManager.replace(R.id.fragmentContainerViewRegistrazione, RegistrazioneRistoranteFragment())
            ristoranteManager.commit()
            binding.utenteScreen.isEnabled = true
        }

        return binding.root
    }

}

class RegistrazioneRistoranteFragment:Fragment(R.layout.ristorante_registrazione_fragment){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : RistoranteRegistrazioneFragmentBinding = RistoranteRegistrazioneFragmentBinding.inflate(inflater, container, false)
        val viewModel = GestoreLogin(this.requireActivity().application)
        val viewModelRistorante = GestoreRicerca(this.requireActivity().application)
        binding.ristoranteRegistrazione.setOnClickListener{
            if(binding.ristoranteConfermaPassword.toString() != binding.ristorantePasswordInsert.toString())
                Snackbar.make(this.requireView(), "Errore nella conferma della password", Snackbar.LENGTH_SHORT).show()
            else{

                var check = false
                val ristoranteObserver = Observer<List<Ristorante>>{
                    for (ristorante in it){
                        if (ristorante.username == binding.ristoranteUsernameInsert.toString())
                            check = true
                    }
                }
                viewModelRistorante.ristoranteList.observe(this.viewLifecycleOwner, ristoranteObserver)
                viewModelRistorante.getAllRistoranti()
                if(check)
                    Snackbar.make(this.requireView(), "Esiste già un ristorante con questo nome", Snackbar.LENGTH_SHORT).show()
                else{
                    if(binding.ristoranteUsernameInsert.toString()=="" || binding.ristorantePasswordInsert.toString()==""|| binding.ristoranteNomeInsert.toString()==""|| binding.ristoranteCittaInsert.toString()=="" || binding.ristoranteIndirizzoInsert.toString()=="")
                        Snackbar.make(this.requireView(), "Username, password, nome, citta' e indirizzo sono parametri obbligatori", Snackbar.LENGTH_LONG).show()
                    else
                        viewModel.insertRistorante(Ristorante(0, binding.ristoranteUsernameInsert.toString(), binding.ristorantePasswordInsert.toString(), binding.ristoranteNomeInsert.toString(), binding.ristoranteDescrizioneInsert.toString(), binding.ristoranteMenuInsert.toString(), binding.ristoranteCapInsert.toString(), binding.ristoranteCittaInsert.toString(), binding.ristoranteIndirizzoInsert.toString(), binding.ristoranteCivicoInsert.toString(), binding.ristoranteEmailInsert.toString(), binding.ristoranteTelefonoInsert.toString(), binding.ristoranteTipologiaInsert.toString()))
                }
            }

        }
        return binding.root
    }

}

class RegistrazioneUtenteFragment:Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : UtenteRegistrazioneFragmentBinding = UtenteRegistrazioneFragmentBinding.inflate(inflater, container, false)
        val viewModel = GestoreLogin(this.requireActivity().application)
        binding.utenteRegistrazione.setOnClickListener{
            if(binding.utentePasswordInsert.toString() != binding.utenteConfermaPasswordInsert.toString())
                Snackbar.make(this.requireView(), "Errore nella conferma della password", Snackbar.LENGTH_SHORT).show()
            else{
                val utenteModel = GestoreUtente(this.requireActivity().application)
                var check=false

                val observer = Observer<List<Utente>> {
                    for(utente in it){
                        if (utente.username == binding.utenteUsernameInsert.toString())
                            check=true
                        }
                    }
                utenteModel.utenteList.observe(this.viewLifecycleOwner, observer)
                utenteModel.readAllUtenti()
                if(check)
                    Snackbar.make(this.requireView(), "Esiste già un utente con questo username", Snackbar.LENGTH_SHORT).show()
                else{
                    if(binding.utenteUsernameInsert.toString()=="" || binding.utentePasswordInsert.toString()=="" || binding.utenteNomeInsert.toString()=="" || binding.utenteCognomeInsert.toString()=="")
                        Snackbar.make(this.requireView(), "Username, password, nome e cognome sono parametri obbligatori", Snackbar.LENGTH_LONG).show()
                    else
                        viewModel.insertUtente(Utente(binding.utenteUsernameInsert.toString(), binding.utentePasswordInsert.toString(), binding.utenteNomeInsert.toString(), binding.utenteCognomeInsert.toString(), binding.utenteEmailInsert.toString(), binding.utenteTelefonoInsert.toString()))
                }

            }
        }
        return binding.root

    }

}

