package com.univalle.dogapp.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.univalle.dogapp.database.CitaDatabase
import com.univalle.dogapp.model.Cita
import kotlinx.coroutines.launch

class HomeAdminCitasViewModel(application: Application) : AndroidViewModel(application) {
    private val citaDao = CitaDatabase.getDatabase(application).citaDao()
    val citas: LiveData<List<Cita>> = citaDao.getAllCitas()

    fun agregarCita(cita: Cita) {
        viewModelScope.launch {
            citaDao.insertCita(cita)
        }
    }

    fun editarCita(citaEditada: Cita) {
        viewModelScope.launch {
            citaDao.updateCita(citaEditada)
        }
    }

    fun eliminarCita(cita: Cita) {
        viewModelScope.launch {
            citaDao.deleteCita(cita)
        }
    }
}