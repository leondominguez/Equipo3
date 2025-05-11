package com.univalle.dogapp.view.create

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.univalle.dogapp.R
import com.univalle.dogapp.databinding.FragmentNuevaCitaBinding
import com.univalle.dogapp.model.Cita
import com.univalle.dogapp.viewmodel.NuevaCitaViewModel

class NuevaCitaFragment : Fragment() {

    private var _binding: FragmentNuevaCitaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NuevaCitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevaCitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_nuevaCitaFragment_to_homeAdminCitasFragment2)
        }

        viewModel.fetchDogBreeds()

        viewModel.listaRazas.observe(viewLifecycleOwner) { lista ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                lista
            )
            binding.ietRaza.setAdapter(adapter)
        }

        val sintomas = listOf(
            "Síntomas", "Solo duerme", "No come", "Fractura extremidad",
            "Tiene pulgas", "Tiene garrapatas", "Bota demasiado pelo"
        )
        val sintomasAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sintomas)
        sintomasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSintomas.adapter = sintomasAdapter

        val fields = listOf(binding.ietMascota, binding.ietRaza, binding.ietPropietario, binding.ietTelefono)
        fields.forEach { field ->
            field.addTextChangedListener {
                binding.btnGuardar.isEnabled = fields.all { it.text.toString().trim().isNotEmpty() }
            }
        }

        binding.btnGuardar.setOnClickListener {
            Log.d("NuevaCitaFragment", "Botón Guardar clickeado")
            if (binding.spinnerSintomas.selectedItem == "Síntomas") {
                Toast.makeText(requireContext(), "Selecciona un síntoma", Toast.LENGTH_SHORT).show()
            } else {
                guardarCita()
            }
        }

        binding.ietMascota.requestFocus()
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.ietMascota, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun guardarCita() {
        Log.d("NuevaCitaFragment", "Método guardarCita iniciado")
        val raza = binding.ietRaza.text.toString()
        viewModel.fetchRandomImage(raza) { imageUrl ->
            Log.d("NuevaCitaFragment", "Imagen obtenida: $imageUrl")
            val cita = Cita(
                mascota = binding.ietMascota.text.toString(),
                raza = raza,
                propietario = binding.ietPropietario.text.toString(),
                telefono = binding.ietTelefono.text.toString(),
                sintoma = binding.spinnerSintomas.selectedItem.toString(),
                imagen = imageUrl
            )
            Log.d("NuevaCitaFragment", "Datos de la cita: $cita")
            viewModel.guardarCita(cita)
            findNavController().navigate(R.id.action_nuevaCitaFragment_to_homeAdminCitasFragment2)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}