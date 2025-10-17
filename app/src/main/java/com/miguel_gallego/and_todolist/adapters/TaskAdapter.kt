package com.miguel_gallego.and_todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miguel_gallego.and_todolist.data.Task
import com.miguel_gallego.and_todolist.databinding.ItemTaskBinding

class TaskAdapter(
    var items: List<Task>,
    val onClick: (Int, List<Task>) -> Unit,
    val onCheck: (Int, List<Task>) -> Unit,
    val onDelete: (Int, List<Task>) -> Unit
): RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClick(position, items)
        }
        holder.binding.checkDone.setOnCheckedChangeListener { _, _ ->
            onCheck(position, items)
        }
        holder.binding.btnDelete.setOnClickListener {
            onDelete(position, items)
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<Task>) {
        this.items = items
        notifyDataSetChanged()  // like .reloadData()
    }
}


class TaskViewHolder(
    val binding: ItemTaskBinding
): RecyclerView.ViewHolder(binding.root) {

    fun render(task: Task) {
        binding.tvTitle.text = task.title
        binding.checkDone.isChecked = task.done
    }
}