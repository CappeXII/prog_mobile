package com.example.justsit.views

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.justsit.databinding.ActivityVistaModificaTavoloBinding
import com.example.justsit.models.Tavolo
import com.example.justsit.viewmodels.GestioneRistorante

class VistaModificaTavolo : AppCompatActivity() {

    private lateinit var binding: ActivityVistaModificaTavoloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaModificaTavoloBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("id", 0)
        val viewModel = GestioneRistorante(this.application)
        val tavoli = emptyArray<Tavolo>()
        binding.tavoloUpdateBtn.isEnabled=false
        binding.tavoloDeleteBtn.isEnabled=false
        val tavoloObserver = Observer<List<Tavolo>>{
            for(tavolo in it){
                tavoli[tavoli.lastIndex+1] = tavolo
            }
            binding.newTavoloNumero.text = StringBuilder("TAVOLO NUMERO ").append(tavoli[tavoli.lastIndex].tavolo+1)
            val array = emptyArray<String>()
            for(tavolo in tavoli){
                val stringa =StringBuilder("TAVOLO ").append(tavolo.tavolo)
                array[array.lastIndex+1] = stringa.toString()
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, array)
            binding.modificaTavoloList.adapter = adapter
        }
        viewModel.listTavolo.observe(this, tavoloObserver)
        viewModel.readTavoli(id)
        binding.modificaTavoloBack.setOnClickListener{
            finish()
        }
        binding.modificaTavoloList.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.tavoloUpdateBtn.isEnabled=true
                binding.tavoloDeleteBtn.isEnabled=true
                binding.modificaTavoloNPersone.setText(tavoli[p2].npersone)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.tavoloUpdateBtn.isEnabled=false
                binding.tavoloDeleteBtn.isEnabled=false
            }

        }
        binding.tavoloUpdateBtn.setOnClickListener{
            viewModel.updateTavolo(Tavolo(tavoli[binding.modificaTavoloList.selectedItemPosition].tavolo, tavoli[binding.modificaTavoloList.selectedItemPosition].ristorante, binding.modificaTavoloNPersone.text.toString().toInt()))
            Toast.makeText(this, "Tavolo modificato", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.tavoloDeleteBtn.setOnClickListener{
            viewModel.deleteTavolo(tavoli[binding.modificaTavoloList.selectedItemPosition])
            Toast.makeText(this, "Tavolo eliminato", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.tavoloInsertBtn.setOnClickListener{
            viewModel.insertTavolo(Tavolo(tavoli[tavoli.lastIndex].tavolo+1, id, binding.newTavoloNPersone.text.toString().toInt()))
            Toast.makeText(this, "tavolo inserito", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


}