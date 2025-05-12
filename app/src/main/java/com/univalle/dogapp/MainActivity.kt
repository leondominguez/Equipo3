package com.univalle.dogapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.univalle.dogapp.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Forzar tema claro o oscuro (opcional)
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) // Forzar modo oscuro
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // Forzar modo claro

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}