package com.univalle.dogapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Cita(val id: Int, val nombreMascota: String, val descripcion: String, val turno: String)

class HomeAdminCitasViewModel : ViewModel() {
    private val _citas = MutableLiveData<List<Cita>>()
    val citas: LiveData<List<Cita>> get() = _citas

    init {
        _citas.value = listOf(
            Cita(1, "Cory", "Fractura extremidad", "Turno #1"),
            Cita(2, "Gus", "Vacunación", "Turno #2"),
            Cita(3, "Rocky", "Consulta general", "Turno #3")
        )
    }

    fun agregarCita(cita: Cita) {
        val listaActual = _citas.value?.toMutableList() ?: mutableListOf()
        listaActual.add(cita)
        _citas.value = listaActual
    }

    fun editarCita(citaEditada: Cita) {
        val listaActual = _citas.value?.toMutableList() ?: mutableListOf()
        val index = listaActual.indexOfFirst { it.id == citaEditada.id }
        if (index != -1) {
            listaActual[index] = citaEditada
            _citas.value = listaActual
        }
    }

    fun eliminarCita(id: Int) {
        val listaActual = _citas.value?.toMutableList() ?: mutableListOf()
        listaActual.removeAll { it.id == id }
        _citas.value = listaActual
    }
}