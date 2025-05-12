package com.univalle.dogapp.view.detail

import android.content.Context
import androidx.lifecycle.LiveData
import com.univalle.dogapp.database.CitaDatabase
import com.univalle.dogapp.model.Cita

class CitaRepository(context: Context) {
    private val citaDao = CitaDatabase.getDatabase(context).citaDao()

    suspend fun updateCita(cita: Cita) = citaDao.updateCita(cita)
    suspend fun deleteCita(cita: Cita) = citaDao.deleteCita(cita)
    fun getCitaById(id: Int): LiveData<Cita?> = citaDao.getCitaById(id)
}