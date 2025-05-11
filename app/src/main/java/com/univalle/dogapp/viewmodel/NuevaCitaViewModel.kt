package com.univalle.dogapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.univalle.dogapp.database.CitaDatabase
import com.univalle.dogapp.model.Cita
import com.univalle.dogapp.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class NuevaCitaViewModel(application: Application) : AndroidViewModel(application) {

    private val _listaRazas = MutableLiveData<List<String>>()
    val listaRazas: LiveData<List<String>> get() = _listaRazas

    fun fetchDogBreeds() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getDogBreeds()
                if (response.isSuccessful) {
                    val breeds = response.body()?.message?.flatMap { (key, subBreeds) ->
                        if (subBreeds.isEmpty()) listOf(key) else subBreeds.map { "$key $it" }
                    } ?: emptyList()
                    _listaRazas.postValue(breeds)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private suspend fun obtenerImagenAleatoria(callback: (String) -> Unit) {
        try {
            val response = RetrofitInstance.api.getRandomImage()
            if (response.isSuccessful) {
                val imageUrl = response.body()?.message ?: ""
                Log.d("NuevaCitaViewModel", "Imagen aleatoria obtenida: $imageUrl")
                callback(imageUrl)
            } else {
                Log.e("NuevaCitaViewModel", "Error en la respuesta de imagen aleatoria: ${response.errorBody()?.string()}")
                callback("") // Retornar vacío si falla
            }
        } catch (e: Exception) {
            Log.e("NuevaCitaViewModel", "Error al obtener imagen aleatoria: ${e.message}", e)
            callback("") // Retornar vacío si falla
        }
    }

    fun fetchRandomImage(raza: String, callback: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val formattedRaza = URLEncoder.encode(raza.replace(" ", "-").lowercase(), StandardCharsets.UTF_8.toString())
                Log.d("NuevaCitaViewModel", "Raza formateada: $formattedRaza")

                // Intentar obtener imagen por raza
                val response = RetrofitInstance.api.getRandomImageByBreed(formattedRaza)
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.message ?: ""
                    Log.d("NuevaCitaViewModel", "Imagen obtenida por raza: $imageUrl")
                    callback(imageUrl)
                } else {
                    Log.e("NuevaCitaViewModel", "Error en la respuesta por raza: ${response.errorBody()?.string()}")
                    obtenerImagenAleatoria(callback) // Fallback a imagen aleatoria
                }
            } catch (e: Exception) {
                Log.e("NuevaCitaViewModel", "Error al obtener imagen por raza: ${e.message}", e)
                obtenerImagenAleatoria(callback) // Fallback a imagen aleatoria
            }
        }
    }

    fun guardarCita(cita: Cita) {
        viewModelScope.launch {
            try {
                Log.d("NuevaCitaViewModel", "Guardando cita: $cita")
                val citaDao = CitaDatabase.getDatabase(getApplication()).citaDao()
                citaDao.insertCita(cita)
                Log.d("NuevaCitaViewModel", "Cita guardada exitosamente")
            } catch (e: Exception) {
                Log.e("NuevaCitaViewModel", "Error al guardar la cita: ${e.message}", e)
            }
        }
    }
}