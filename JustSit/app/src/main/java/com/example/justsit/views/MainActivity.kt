package com.example.justsit.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.justsit.R
import com.example.justsit.databinding.*
import com.example.justsit.models.Ristorante
import com.example.justsit.models.Utente
import com.example.justsit.viewmodels.GestoreLogin
import com.example.justsit.viewmodels.GestoreRicerca
import com.example.justsit.viewmodels.GestoreUtente
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            lifecycleScope.launch(Dispatchers.Main){
            when( viewModel.login(binding.usernameText.text.toString(), binding.passwordText.text.toString())) {
                "utente" -> {
                    val intent = Intent(this@LoginFragment.context, HomeUtente::class.java)
                    intent.putExtra("username", binding.usernameText.text.toString())
                    startActivity(intent)

                }
                "ristorante" -> {
                    val intent = Intent(this@LoginFragment.context, HomeRistorante::class.java)
                    intent.putExtra("username", binding.usernameText.text.toString())
                    startActivity(intent)
                }
                "errore" -> Snackbar.make(
                    this@LoginFragment.requireView(),
                    "Errore nelle credenziali",
                    Snackbar.LENGTH_SHORT
                ).show()
                "wait" -> Snackbar.make(this@LoginFragment.requireView(), "Caricamento...", Snackbar.LENGTH_SHORT)
                    .show()
            }
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
            if(binding.ristoranteConfermaPassword.text.equals(binding.ristorantePasswordInsert.text))
                Snackbar.make(this.requireView(), "Errore nella conferma della password", Snackbar.LENGTH_SHORT).show()
            else{

                var check = false
                val ristoranteObserver = Observer<List<Ristorante>>{
                    for (ristorante in it){
                        if (ristorante.username == binding.ristoranteUsernameInsert.text.toString())
                            check = true
                    }
                }
                viewModelRistorante.ristoranteList.observe(this.viewLifecycleOwner, ristoranteObserver)

                viewModelRistorante.getAllRistoranti()
                if(check)
                    Snackbar.make(this.requireView(), "Esiste gi?? un ristorante con questo nome", Snackbar.LENGTH_SHORT).show()
                else{
                    if(binding.ristoranteUsernameInsert.text.toString()=="" || binding.ristorantePasswordInsert.text.toString()==""|| binding.ristoranteNomeInsert.text.toString()==""|| binding.ristoranteCittaInsert.text.toString()=="" || binding.ristoranteIndirizzoInsert.text.toString()=="")
                        Snackbar.make(this.requireView(), "Username, password, nome, citta' e indirizzo sono parametri obbligatori", Snackbar.LENGTH_LONG).show()
                    else
                        viewModel.insertRistorante(Ristorante(0, binding.ristoranteUsernameInsert.text.toString(), binding.ristorantePasswordInsert.text.toString(), binding.ristoranteNomeInsert.text.toString(), binding.ristoranteDescrizioneInsert.text.toString(), binding.ristoranteMenuInsert.text.toString(), binding.ristoranteCapInsert.text.toString(), binding.ristoranteCittaInsert.text.toString(), binding.ristoranteIndirizzoInsert.text.toString(), binding.ristoranteCivicoInsert.text.toString(), binding.ristoranteEmailInsert.text.toString(), binding.ristoranteTelefonoInsert.text.toString(), binding.ristoranteTipologiaInsert.text.toString()))
                        val intent = Intent(this.context, HomeRistorante::class.java)
                        intent.putExtra("username", binding.ristoranteUsernameInsert.text.toString())
                        startActivity(intent)
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
            if(binding.utentePasswordInsert.text.toString() != binding.utenteConfermaPasswordInsert.text.toString())
                Snackbar.make(this.requireView(), "Errore nella conferma della password", Snackbar.LENGTH_SHORT).show()
            else{
                val utenteModel = GestoreUtente(this.requireActivity().application)
                var check=false

                val observer = Observer<List<Utente>> {
                    for(utente in it){
                        if (utente.username == binding.utenteUsernameInsert.text.toString())
                            check=true
                        }
                    }
                utenteModel.utenteList.observe(this.viewLifecycleOwner, observer)
                utenteModel.readAllUtenti()
                if(check)
                    Snackbar.make(this.requireView(), "Esiste gi?? un utente con questo username", Snackbar.LENGTH_SHORT).show()
                else{
                    if(binding.utenteUsernameInsert.text.toString()=="" || binding.utentePasswordInsert.text.toString()=="" || binding.utenteNomeInsert.text.toString()=="" || binding.utenteCognomeInsert.text.toString()=="")
                        Snackbar.make(this.requireView(), "Username, password, nome e cognome sono parametri obbligatori", Snackbar.LENGTH_LONG).show()
                    else
                        viewModel.insertUtente(Utente(binding.utenteUsernameInsert.text.toString(), binding.utentePasswordInsert.text.toString(), binding.utenteNomeInsert.text.toString(), binding.utenteCognomeInsert.text.toString(), binding.utenteEmailInsert.text.toString(), binding.utenteTelefonoInsert.text.toString()))
                        val intent = Intent(this.context, HomeUtente::class.java)
                        intent.putExtra("username", binding.utenteUsernameInsert.text.toString())
                        startActivity(intent)
                }

            }
        }
        return binding.root

    }

}

