package com.example.justsit.views

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.justsit.databinding.ActivityVistaModificaTurnoBinding
import com.example.justsit.models.Turno
import com.example.justsit.viewmodels.GestioneRistorante
import java.text.SimpleDateFormat
import java.util.*

class VistaModificaTurno : AppCompatActivity() {

    private lateinit var binding: ActivityVistaModificaTurnoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaModificaTurnoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("id", 0)
        val viewModel = GestioneRistorante(this.application)
        val turni = emptyList<Turno>().toMutableList()
        val formatter = SimpleDateFormat("hh:mm")
        binding.turnoUpdateBtn.isEnabled=false
        binding.turnoDeleteBtn.isEnabled=false
        val turnoObserver = Observer<List<Turno>>{
            for(turno in it){
                turni.add(turno)
            }
            if(turni.isNotEmpty())
            binding.newTurnoNumero.text = StringBuilder("TURNO NUMERO ").append(turni[turni.lastIndex].turno+1)
            else
                binding.newTurnoNumero.text = "TURNO NUMERO 1"
            val array = emptyList<String>().toMutableList()
            for(turno in turni){
                val stringa =StringBuilder("TURNO ").append(turno.turno)
                array.add(stringa.toString())
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, array)
            binding.modificaTurnoList.adapter = adapter
        }
        viewModel.listTurno.observe(this, turnoObserver)
        viewModel.readTurni(id)
        binding.modificaTurnoBack.setOnClickListener{
            finish()
        }
        binding.modificaTurnoList.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.turnoUpdateBtn.isEnabled=true
                binding.turnoDeleteBtn.isEnabled=true
                binding.modificaTurnoOrarioInizio.setText(formatter.format(Date(turni[p2].orarioinizio)).toString())
                binding.modificaTurnoOrarioFine.setText(formatter.format(Date(turni[p2].orariofine)).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.turnoUpdateBtn.isEnabled=false
                binding.turnoDeleteBtn.isEnabled=false
            }

        }

        binding.turnoUpdateBtn.setOnClickListener{

            viewModel.updateTurno(Turno(turni[binding.modificaTurnoList.selectedItemPosition].turno, turni[binding.modificaTurnoList.selectedItemPosition].ristorante,
                formatter.parse(binding.modificaTurnoOrarioInizio.text.toString())?.time!!,
                formatter.parse(binding.modificaTurnoOrarioFine.text.toString())?.time!!
            ))
            Toast.makeText(this, "Turno modificato", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.turnoDeleteBtn.setOnClickListener{
            viewModel.deleteTurno(turni[binding.modificaTurnoList.selectedItemPosition])
            Toast.makeText(this, "Turno eliminato", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.turnoInsertBtn.setOnClickListener {
            if (turni.isNotEmpty())
                viewModel.insertTurno(Turno(turni[turni.lastIndex].turno + 1, id, formatter.parse(binding.newTurnoOrarioInizio.text.toString())?.time!!, formatter.parse(binding.newTurnoOrarioFine.text.toString())?.time!!))
            else
                viewModel.insertTurno(Turno(0, id, formatter.parse(binding.newTurnoOrarioInizio.text.toString())?.time!!, formatter.parse(binding.newTurnoOrarioFine.text.toString())?.time!!))
            Toast.makeText(this, "Turno inserito", Toast.LENGTH_SHORT).show()
            finish()
        }

        }
    }