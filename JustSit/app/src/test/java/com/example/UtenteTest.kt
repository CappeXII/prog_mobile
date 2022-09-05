package com.example

import android.app.Application
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.justsit.models.Utente
import com.example.justsit.viewmodels.GestoreLogin
import com.example.justsit.viewmodels.GestoreUtente
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class UtenteTest {
    private val application = mock(Application::class.java)
    private val controllore = GestoreUtente(application)
    private val login = GestoreLogin(application)
    @Before
    fun setUp(){
        login.insertUtente(Utente("user1", "password", "Utente", "1", "utente1@gmail.com", "3333333333"))
    }
    @After
    fun tearDown(){
        val observer = Observer<List<Utente>>{
            for(utente in it)
                controllore.delete(utente)
        }
        controllore.utenteList.observe(application.getSystemService<LifecycleOwner>()!!, observer)
        controllore.readAllUtenti()
    }
    @Test
    fun update(){
        val observer = Observer<Utente>{
            assert(it.cognome=="Cappelletti" || it.nome=="Maurizio")
        }
        controllore.utente.observe(application.getSystemService<LifecycleOwner>()!!, observer)
        controllore.update(Utente("user1", "password", "Maurizio", "Cappelletti", "utente1@gmail.com", "3333333333"))
    }
    @Test
    fun delete(){
        val observer = Observer<List<Utente>>{
            val list = mutableListOf<String>()
            for(utente in it)
                list.add(utente.username)
            assert("user1" !in list)
        }
        val observerUtente = Observer<Utente>{
            controllore.delete(it)
        }
        controllore.utenteList.observe(application.getSystemService<LifecycleOwner>()!!, observer)
        controllore.utente.observe(application.getSystemService<LifecycleOwner>()!!, observerUtente)
        controllore.readUtente("user1")
    }

}