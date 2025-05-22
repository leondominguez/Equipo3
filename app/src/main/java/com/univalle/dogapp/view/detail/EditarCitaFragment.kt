package com.univalle.dogapp.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.univalle.dogapp.R
import com.univalle.dogapp.databinding.FragmentEditarCitaBinding
import com.univalle.dogapp.network.RetrofitInstance
import com.univalle.dogapp.viewmodel.EditarCitaViewModel
import kotlinx.coroutines.launch

class EditarCitaFragment : Fragment() {

    private var _binding: FragmentEditarCitaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditarCitaViewModel by viewModels()
    private val args: EditarCitaFragmentArgs by navArgs()
    private var allBreeds: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditarCitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar
        binding.toolbar.btnVolver.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.root.findViewById<TextView>(R.id.toolbar_title).text =
            getString(R.string.editar_cita)

        // Obtener razas y configurar autocompletado dinámico
        viewModel.fetchDogBreeds()
        viewModel.breeds.observe(viewLifecycleOwner) { breeds ->
            allBreeds = breeds
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breeds)
            binding.ietRaza.setAdapter(adapter)
        }

        // Filtrado dinámico mientras se escribe
        binding.ietRaza.addTextChangedListener { text ->
            val filtro = text?.toString()?.lowercase() ?: ""
            val razasFiltradas = allBreeds.filter { it.lowercase().startsWith(filtro) }
            val nuevoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, razasFiltradas)
            binding.ietRaza.setAdapter(nuevoAdapter)
            if (filtro.isNotEmpty()) binding.ietRaza.showDropDown()
        }
        binding.btnEditarCita.isEnabled=false
        binding.btnEditarCita.alpha=0.5f

        // Cargar datos de la cita por id
        viewModel.getCitaById(args.citaId).observe(viewLifecycleOwner) { cita ->
            if (cita != null) {
                binding.ietMascota.setText(cita.mascota)
                binding.ietRaza.setText(cita.raza)
                binding.ietPropietario.setText(cita.propietario)
                binding.ietTelefono.setText(cita.telefono)


                // Se guardan los valores originales en una variable, para luego comparar
                val originalValues = mapOf(
                    binding.ietMascota to binding.ietMascota.text.toString(),
                    binding.ietRaza to binding.ietRaza.text.toString(),
                    binding.ietPropietario to binding.ietPropietario.text.toString(),
                    binding.ietTelefono to binding.ietTelefono.text.toString()
                )
                // Se guardan los valores originales en una lista
                val fields = originalValues.keys.toList()
                // Se recorre la lista y se comparan los valores cuando hay cambios en el campo con los valores originales
                // si ocurre un cambio y no coincide se activa
                fields.forEach { field ->
                    field.addTextChangedListener {
                        val hasChanged = fields.any { it.text.toString() != originalValues[it] }
                        binding.btnEditarCita.isEnabled = hasChanged
                        binding.btnEditarCita.alpha = if (hasChanged) 1.0f else 0.5f
                    }
                }

                // Acción del botón Editar Cita
                binding.btnEditarCita.setOnClickListener {
                    val nuevaRaza = binding.ietRaza.text.toString()
                    if (nuevaRaza != cita.raza) {
                        // Si la raza cambió, obtener nueva imagen
                        lifecycleScope.launch {
                            val response = RetrofitInstance.api.getRandomImageByBreed(nuevaRaza.lowercase())
                            val nuevaImagen = if (response.isSuccessful) response.body()?.message ?: cita.imagen else cita.imagen
                            val updatedCita = cita.copy(
                                mascota = binding.ietMascota.text.toString(),
                                raza = nuevaRaza,
                                propietario = binding.ietPropietario.text.toString(),
                                telefono = binding.ietTelefono.text.toString(),
                                imagen = nuevaImagen
                            )
                            viewModel.updateCita(updatedCita) {
                                findNavController().navigate(R.id.action_editarCitaFragment_to_homeAdminCitasFragment2)
                            }
                        }
                    } else {
                        // Si la raza no cambió, solo actualizar los demás campos
                        val updatedCita = cita.copy(
                            mascota = binding.ietMascota.text.toString(),
                            raza = nuevaRaza,
                            propietario = binding.ietPropietario.text.toString(),
                            telefono = binding.ietTelefono.text.toString()
                        )
                        viewModel.updateCita(updatedCita) {
                            findNavController().navigate(R.id.action_editarCitaFragment_to_homeAdminCitasFragment2)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}