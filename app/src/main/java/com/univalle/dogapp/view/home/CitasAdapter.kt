package com.univalle.dogapp.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.univalle.dogapp.databinding.ItemCitaBinding
import com.univalle.dogapp.model.Cita

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
            binding.nameCory.text = cita.mascota
            binding.descriptionCory.text = cita.sintoma

            // Formatear el ID como "# 1", "# 2", etc.
            binding.itemNumber.text = "# ${cita.id}"

            // Cargar la imagen usando Glide
            Glide.with(binding.root.context)
                .load(cita.imagen) // URL de la imagen
                .placeholder(android.R.drawable.progress_indeterminate_horizontal) // Imagen de carga
                .error(android.R.drawable.stat_notify_error) // Imagen de error
                .into(binding.imgCory) // ShapeableImageView
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