package com.univalle.dogapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.univalle.dogapp.model.Cita

@Dao
interface CitaDao {

    @Query("SELECT * FROM citas")
    fun getAllCitas(): LiveData<List<Cita>>

    @Query("SELECT * FROM citas WHERE id = :id")
    fun getCitaById(id: Int): LiveData<Cita?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCita(cita: Cita)

    @Update
    suspend fun updateCita(cita: Cita)

    @Delete
    suspend fun deleteCita(cita: Cita)
}