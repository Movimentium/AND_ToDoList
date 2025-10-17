package com.miguel_gallego.and_todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miguel_gallego.and_todolist.databinding.ItemHeaderBinding

class HeaderAdapter(var title: String): RecyclerView.Adapter<HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHeaderBinding.inflate(layoutInflater, parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.render(title)
    }

    override fun getItemCount() = 1
}

class HeaderViewHolder(val binding: ItemHeaderBinding): RecyclerView.ViewHolder(binding.root) {

    fun render(title: String) {
        binding.tvHeaderTitle.text = title
    }
}