package com.univalle.dogapp.view.home

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.univalle.dogapp.R
import com.univalle.dogapp.databinding.FragmentHomeAdminCitasBinding
import com.univalle.dogapp.viewmodel.HomeAdminCitasViewModel
import com.univalle.dogapp.utils.SpacingItemDecoration

class HomeAdminCitasFragment : Fragment() {

    private var _binding: FragmentHomeAdminCitasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeAdminCitasViewModel by viewModels()
    private lateinit var adapter: CitasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAdminCitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Interceptar el botón "volver" del sistema
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish() // Cierra la actividad y regresa al escritorio
            }
        })

        adapter = CitasAdapter()
        binding.recyclerViewCitas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCitas.adapter = adapter
        binding.recyclerViewCitas.addItemDecoration(SpacingItemDecoration(16)) // Espaciado de 16dp

        viewModel.citas.observe(viewLifecycleOwner) { citas ->
            adapter.submitList(citas)
        }
        binding.btnNuevaCita.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdminCitasFragment2_to_nuevaCitaFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}