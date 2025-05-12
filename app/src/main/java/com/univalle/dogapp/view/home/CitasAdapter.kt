package com.univalle.dogapp.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.univalle.dogapp.databinding.ItemCitaBinding
import com.univalle.dogapp.model.Cita

class CitasAdapter(
    private val onCitaClick: (Cita) -> Unit
) : ListAdapter<Cita, CitasAdapter.CitaViewHolder>(CitaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val binding = ItemCitaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        holder.bind(getItem(position), onCitaClick)
    }

    class CitaViewHolder(private val binding: ItemCitaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cita: Cita, onCitaClick: (Cita) -> Unit) {
            binding.nameCory.text = cita.mascota
            binding.descriptionCory.text = cita.sintoma
            binding.itemNumber.text = "# ${cita.id}"

            Glide.with(binding.root.context)
                .load(cita.imagen)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.imgCory)

            binding.root.setOnClickListener {
                onCitaClick(cita)
            }
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