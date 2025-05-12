package com.univalle.dogapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.univalle.dogapp.model.Cita
import java.util.concurrent.Executors


@Database(entities = [Cita::class], version = 1, exportSchema = true)
abstract class CitaDatabase : RoomDatabase() {
    abstract fun citaDao(): CitaDao

    companion object {
        @Volatile
        private var INSTANCE: CitaDatabase? = null

        fun getDatabase(context: Context): CitaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CitaDatabase::class.java,
                    "cita_database"
                )
                    .setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                        Log.d("RoomQuery", "Query: $sqlQuery Args: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}