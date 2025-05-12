package com.univalle.dogapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.univalle.dogapp.database.CitaDatabase
import com.univalle.dogapp.model.Cita
import com.univalle.dogapp.network.RetrofitInstance
import kotlinx.coroutines.launch

class EditarCitaViewModel(application: Application) : AndroidViewModel(application) {

    private val citaDao = CitaDatabase.getDatabase(application).citaDao()

    private val _breeds = MutableLiveData<List<String>>()
    val breeds: LiveData<List<String>> get() = _breeds

    fun fetchDogBreeds() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getDogBreeds()
                if (response.isSuccessful) {
                    _breeds.value = response.body()?.message?.keys?.toList() ?: emptyList()
                } else {
                    _breeds.value = emptyList()
                }
            } catch (e: Exception) {
                _breeds.value = emptyList()
            }
        }
    }

    fun getCitaById(id: Int): LiveData<Cita?> = citaDao.getCitaById(id)

    fun updateCita(cita: Cita, onComplete: () -> Unit) {
        viewModelScope.launch {
            citaDao.updateCita(cita)
            onComplete()
        }
    }
}