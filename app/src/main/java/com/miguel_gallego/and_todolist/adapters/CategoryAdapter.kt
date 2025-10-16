package com.miguel_gallego.and_todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miguel_gallego.and_todolist.data.Category
import com.miguel_gallego.and_todolist.databinding.ItemCategoryBinding

class CategoryAdapter(
    var items: List<Category>,
    val onClick: (Int) -> Unit,
    val onEdit: (Int) -> Unit,
    val onDelete: (Int) -> Unit
): RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
        holder.binding.btnEdit.setOnClickListener {
            onEdit(position)
        }
        holder.binding.btnDelete.setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()  // like .reloadData()
    }
}


class CategoryViewHolder(
    val binding: ItemCategoryBinding
): RecyclerView.ViewHolder(binding.root) {

    fun render(category: Category) {
        binding.tvName.text = category.name
    }
}