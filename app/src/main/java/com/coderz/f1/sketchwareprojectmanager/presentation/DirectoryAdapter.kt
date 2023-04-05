package com.coderz.f1.sketchwareprojectmanager.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coderz.f1.sketchwareprojectmanager.databinding.RecylerItemBinding


class DirectoryAdapter(private val onItemClicked:(DocumentFile) -> Unit): ListAdapter<DocumentFile, DirectoryAdapter.ViewHolder>(
    DiffUtilCallback()
) {


    inner class ViewHolder(private val binding: RecylerItemBinding,onItemClicked:(Int) -> Unit): RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                root.setOnClickListener{
                    onItemClicked(adapterPosition)
                }
            }
        }

        fun bind(documentFile: DocumentFile){
            binding.apply {
                txtProjectName.text = documentFile.name
            }
        }
    }

    class DiffUtilCallback: DiffUtil.ItemCallback<DocumentFile>(){
        override fun areContentsTheSame(oldItem: DocumentFile, newItem: DocumentFile): Boolean = oldItem.name == newItem.name

        override fun areItemsTheSame(oldItem: DocumentFile, newItem: DocumentFile): Boolean = oldItem == newItem
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecylerItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding){position ->
            onItemClicked(getItem(position))
        }
    }
}