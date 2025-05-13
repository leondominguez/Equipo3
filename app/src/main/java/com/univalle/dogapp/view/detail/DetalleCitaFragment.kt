package com.univalle.dogapp.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.univalle.dogapp.R
import com.univalle.dogapp.databinding.FragmentDetalleCitaBinding
import com.univalle.dogapp.model.Cita
import com.univalle.dogapp.viewmodel.DetalleCitaViewModel
// import dagger.hilt.android.AndroidEntryPoint // Eliminado

// @AndroidEntryPoint // Eliminado
class DetalleCitaFragment : Fragment() {

    private var _binding: FragmentDetalleCitaBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleCitaFragmentArgs by navArgs()
    private val viewModel: DetalleCitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleCitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val citaId = args.citaId

        viewModel.getCitaById(citaId).observe(viewLifecycleOwner) { cita: Cita? ->
            if (cita != null) {
                // Configurar la toolbar
                val toolbarTitle = binding.toolbar.root.findViewById<TextView>(R.id.toolbar_title)
                toolbarTitle.text = cita.mascota
                val btnVolver = binding.toolbar.root.findViewById<ImageView>(R.id.btn_volver)
                btnVolver.setOnClickListener { findNavController().navigateUp() }

                // Mostrar datos de la cita
                Glide.with(this)
                    .load(cita.imagen)
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                    .error(android.R.drawable.stat_notify_error)
                    .into(binding.dogImage)

                binding.turno.text = "#: ${cita.id}"
                binding.dogBreed.text = "Raza: ${cita.raza}"
                binding.dogDescription.text = "Síntomas: ${cita.sintoma}"
                binding.dogOwner.text  = "Propietario: ${cita.propietario}"
                binding.dogPhone.text = "Teléfono: ${cita.telefono}"

                // Botón de eliminar
                binding.deleteButton.setOnClickListener {
                    val dialog = AlertDialog.Builder(requireContext(), R.style.WhiteAlertDialog)
                        .setTitle("Confirmar eliminación")
                        .setMessage("¿Estás seguro de que deseas eliminar esta cita?")
                        .setPositiveButton("Eliminar") { _, _ -> /* ... */ }
                        .setNegativeButton("Cancelar", null)
                        .show()

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_700))
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))

                }

                // Botón de editar: pasar solo el id
                binding.editButton.setOnClickListener {
                    val action = DetalleCitaFragmentDirections.actionDetalleCitaFragmentToEditarCitaFragment(cita.id)
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}