package com.univalle.dogapp.model
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mascota: String,
    val raza: String,
    val propietario: String,
    val telefono: String,
    val sintoma: String,
    val imagen: String = ""
) : Parcelable