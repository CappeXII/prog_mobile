package com.example.justsit
import android.app.Application
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import com.example.justsit.models.Ristorante
import com.example.justsit.viewmodels.GestioneRistorante
import com.example.justsit.viewmodels.GestoreLogin
import com.example.justsit.viewmodels.GestoreRicerca
import org.junit.After
import org.mockito.Mockito.*
import org.junit.Before
import org.junit.Test


class RistoranteTest {
    private val application = mock(Application::class.java)
    private val login = GestoreLogin(application)
    private val controllore = GestioneRistorante(application)
    @Before
    fun setUp(){

        login.insertRistorante(Ristorante(1, "ristorante1", "ristorante", "Ristorante 1", "Descrizione del primo ristorante", "www.menu.org", "62015", "Monte San Giusto", "Corso Bonafede", "3", "mailto@mail.com", "3333333333", "sushi"))
    }
    @After
    fun tearDown(){
        val controller= GestoreRicerca(application)
        val observer= Observer<List<Ristorante>>{
            for(ristorante in it){
                controllore.deleteRistorante(ristorante)
            }
        }
        controller.ristoranteList.observe(application.getSystemService()!!, observer)
        controller.getAllRistoranti()

    }
    @Test
    fun update(){
        val observer = Observer<Ristorante>{
            assert((it.citta == "Montegranaro" || it.indirizzo == "Corso Libertà"))
        }
        controllore.ristorante.observe(application.getSystemService()!!, observer)
        controllore.updateRistorante(Ristorante(1, "ristorante1", "ristorante", "Ristorante 1", "Descrizione del primo ristorante", "www.menu.org", "62015", "Montegranaro", "Corso Libertà", "3", "mailto@mail.com", "3333333333", "sushi"))
    }
    @Test
    fun delete(){
        val controller = GestoreRicerca(application)
        val observer = Observer<List<Ristorante>>{
            val array = mutableListOf<Int>()
            for (ristorante in it){
                array.add(ristorante.id_ristorante)
            }
            assert(1 !in array)
        }
        val observerRistorante = Observer<Ristorante>{
            controllore.deleteRistorante(it)
        }
        controllore.ristorante.observe(application.getSystemService()!!, observerRistorante)
        controllore.readRistorante(1)
        controller.ristoranteList.observe(application.getSystemService()!!, observer)
        controller.getAllRistoranti()
    }
}
