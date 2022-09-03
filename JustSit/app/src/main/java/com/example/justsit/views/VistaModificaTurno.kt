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

class VistaModificaTurno : AppCompatActivity() {

    private lateinit var binding: ActivityVistaModificaTurnoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaModificaTurnoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("id", 0)
        val viewModel = GestioneRistorante(this.application)
        val turni = emptyArray<Turno>()
        binding.turnoUpdateBtn.isEnabled=false
        binding.turnoDeleteBtn.isEnabled=false
        val turnoObserver = Observer<List<Turno>>{
            for(turno in it){
                turni[turni.lastIndex+1] = turno
            }
            binding.newTurnoNumero.text = StringBuilder("TURNO NUMERO ").append(turni[turni.lastIndex].turno+1)
            val array = emptyArray<String>()
            for(turno in turni){
                val stringa =StringBuilder("TURNO ").append(turno.turno)
                array[array.lastIndex+1] = stringa.toString()
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
                binding.modificaTurnoOrarioInizio.setText(turni[p2].orarioinizio.toString())
                binding.modificaTurnoOrarioFine.setText(turni[p2].orariofine.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.turnoUpdateBtn.isEnabled=false
                binding.turnoDeleteBtn.isEnabled=false
            }

        }
        val formatter = SimpleDateFormat("hh:mm")
        binding.turnoUpdateBtn.setOnClickListener{

            viewModel.updateTurno(Turno(turni[binding.modificaTurnoList.selectedItemPosition].turno, turni[binding.modificaTurnoList.selectedItemPosition].ristorante,
                formatter.parse(binding.modificaTurnoOrarioInizio.text.toString())!!,
                formatter.parse(binding.modificaTurnoOrarioFine.text.toString())!!
            ))
            Toast.makeText(this, "Turno modificato", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.turnoDeleteBtn.setOnClickListener{
            viewModel.deleteTurno(turni[binding.modificaTurnoList.selectedItemPosition])
            Toast.makeText(this, "Turno eliminato", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.turnoInsertBtn.setOnClickListener{
            viewModel.insertTurno(Turno(turni[turni.lastIndex].turno+1, id,
                formatter.parse(binding.newTurnoOrarioInizio.text.toString())!!, formatter.parse(binding.newTurnoOrarioFine.text.toString())!!
            ))
            Toast.makeText(this, "Turno inserito", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


}