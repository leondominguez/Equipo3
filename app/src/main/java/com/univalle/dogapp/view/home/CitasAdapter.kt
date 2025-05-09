package com.univalle.dogapp.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.univalle.dogapp.databinding.ItemCitaBinding
import com.univalle.dogapp.viewmodel.Cita

class CitasAdapter : ListAdapter<Cita, CitasAdapter.CitaViewHolder>(CitaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val binding = ItemCitaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CitaViewHolder(private val binding: ItemCitaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cita: Cita) {
            binding.nameCory.text = cita.nombreMascota
            binding.descriptionCory.text = cita.descripcion
        }
    }

    class CitaDiffCallback : DiffUtil.ItemCallback<Cita>() {
        override fun areItemsTheSame(oldItem: Cita, newItem: Cita): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cita, newItem: Cita): Boolean {
            return oldItem == newItem
        }
    }
}