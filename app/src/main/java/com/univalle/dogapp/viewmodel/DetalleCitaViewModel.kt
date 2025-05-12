package com.univalle.dogapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.univalle.dogapp.database.CitaDatabase
import com.univalle.dogapp.model.Cita
import kotlinx.coroutines.launch

class DetalleCitaViewModel(application: Application) : AndroidViewModel(application) {
    private val citaDao = CitaDatabase.getDatabase(application).citaDao()

    fun getCitaById(id: Int): LiveData<Cita?> = citaDao.getCitaById(id)

    fun eliminarCita(cita: Cita, onComplete: () -> Unit) {
        viewModelScope.launch {
            citaDao.deleteCita(cita)
            onComplete()
        }
    }

    fun editarCita(cita: Cita, onComplete: () -> Unit) {
        viewModelScope.launch {
            citaDao.updateCita(cita)
            onComplete()
        }
    }
}